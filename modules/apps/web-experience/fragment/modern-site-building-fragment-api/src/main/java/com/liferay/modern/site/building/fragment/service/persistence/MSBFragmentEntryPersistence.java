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

import com.liferay.modern.site.building.fragment.exception.NoSuchFragmentEntryException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the msb fragment entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.modern.site.building.fragment.service.persistence.impl.MSBFragmentEntryPersistenceImpl
 * @see MSBFragmentEntryUtil
 * @generated
 */
@ProviderType
public interface MSBFragmentEntryPersistence extends BasePersistence<MSBFragmentEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MSBFragmentEntryUtil} to access the msb fragment entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the msb fragment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching msb fragment entries
	*/
	public java.util.List<MSBFragmentEntry> findByGroupId(long groupId);

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
	public java.util.List<MSBFragmentEntry> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<MSBFragmentEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

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
	public java.util.List<MSBFragmentEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

	/**
	* Returns the msb fragment entries before and after the current msb fragment entry in the ordered set where groupId = &#63;.
	*
	* @param msbFragmentEntryId the primary key of the current msb fragment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public MSBFragmentEntry[] findByGroupId_PrevAndNext(
		long msbFragmentEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns all the msb fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching msb fragment entries that the user has permission to view
	*/
	public java.util.List<MSBFragmentEntry> filterFindByGroupId(long groupId);

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
	public java.util.List<MSBFragmentEntry> filterFindByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<MSBFragmentEntry> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

	/**
	* Returns the msb fragment entries before and after the current msb fragment entry in the ordered set of msb fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param msbFragmentEntryId the primary key of the current msb fragment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public MSBFragmentEntry[] filterFindByGroupId_PrevAndNext(
		long msbFragmentEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Removes all the msb fragment entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of msb fragment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching msb fragment entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of msb fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching msb fragment entries that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the msb fragment entries where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the matching msb fragment entries
	*/
	public java.util.List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId);

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
	public java.util.List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId, int start, int end);

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
	public java.util.List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

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
	public java.util.List<MSBFragmentEntry> findByMSBFragmentCollectionId(
		long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry findByMSBFragmentCollectionId_First(
		long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the first msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry fetchByMSBFragmentCollectionId_First(
		long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

	/**
	* Returns the last msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry findByMSBFragmentCollectionId_Last(
		long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the last msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry fetchByMSBFragmentCollectionId_Last(
		long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

	/**
	* Returns the msb fragment entries before and after the current msb fragment entry in the ordered set where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentEntryId the primary key of the current msb fragment entry
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public MSBFragmentEntry[] findByMSBFragmentCollectionId_PrevAndNext(
		long msbFragmentEntryId, long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Removes all the msb fragment entries where msbFragmentCollectionId = &#63; from the database.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	*/
	public void removeByMSBFragmentCollectionId(long msbFragmentCollectionId);

	/**
	* Returns the number of msb fragment entries where msbFragmentCollectionId = &#63;.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the number of matching msb fragment entries
	*/
	public int countByMSBFragmentCollectionId(long msbFragmentCollectionId);

	/**
	* Returns all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the matching msb fragment entries
	*/
	public java.util.List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId);

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
	public java.util.List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end);

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
	public java.util.List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

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
	public java.util.List<MSBFragmentEntry> findByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry findByG_MSBFCI_First(long groupId,
		long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry fetchByG_MSBFCI_First(long groupId,
		long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry findByG_MSBFCI_Last(long groupId,
		long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry fetchByG_MSBFCI_Last(long groupId,
		long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

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
	public MSBFragmentEntry[] findByG_MSBFCI_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns all the msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the matching msb fragment entries that the user has permission to view
	*/
	public java.util.List<MSBFragmentEntry> filterFindByG_MSBFCI(long groupId,
		long msbFragmentCollectionId);

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
	public java.util.List<MSBFragmentEntry> filterFindByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end);

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
	public java.util.List<MSBFragmentEntry> filterFindByG_MSBFCI(long groupId,
		long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

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
	public MSBFragmentEntry[] filterFindByG_MSBFCI_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Removes all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	*/
	public void removeByG_MSBFCI(long groupId, long msbFragmentCollectionId);

	/**
	* Returns the number of msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the number of matching msb fragment entries
	*/
	public int countByG_MSBFCI(long groupId, long msbFragmentCollectionId);

	/**
	* Returns the number of msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @return the number of matching msb fragment entries that the user has permission to view
	*/
	public int filterCountByG_MSBFCI(long groupId, long msbFragmentCollectionId);

	/**
	* Returns the msb fragment entry where groupId = &#63; and name = &#63; or throws a {@link NoSuchFragmentEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment entry
	* @throws NoSuchFragmentEntryException if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry findByG_N(long groupId, java.lang.String name)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the msb fragment entry where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry fetchByG_N(long groupId, java.lang.String name);

	/**
	* Returns the msb fragment entry where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry fetchByG_N(long groupId, java.lang.String name,
		boolean retrieveFromCache);

	/**
	* Removes the msb fragment entry where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the msb fragment entry that was removed
	*/
	public MSBFragmentEntry removeByG_N(long groupId, java.lang.String name)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the number of msb fragment entries where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching msb fragment entries
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @return the matching msb fragment entries
	*/
	public java.util.List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name);

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
	public java.util.List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name, int start, int end);

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
	public java.util.List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

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
	public java.util.List<MSBFragmentEntry> findByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache);

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
	public MSBFragmentEntry findByG_MSBFCI_LikeN_First(long groupId,
		long msbFragmentCollectionId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the first msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry fetchByG_MSBFCI_LikeN_First(long groupId,
		long msbFragmentCollectionId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

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
	public MSBFragmentEntry findByG_MSBFCI_LikeN_Last(long groupId,
		long msbFragmentCollectionId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the last msb fragment entry in the ordered set where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching msb fragment entry, or <code>null</code> if a matching msb fragment entry could not be found
	*/
	public MSBFragmentEntry fetchByG_MSBFCI_LikeN_Last(long groupId,
		long msbFragmentCollectionId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

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
	public MSBFragmentEntry[] findByG_MSBFCI_LikeN_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Returns all the msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @return the matching msb fragment entries that the user has permission to view
	*/
	public java.util.List<MSBFragmentEntry> filterFindByG_MSBFCI_LikeN(
		long groupId, long msbFragmentCollectionId, java.lang.String name);

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
	public java.util.List<MSBFragmentEntry> filterFindByG_MSBFCI_LikeN(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		int start, int end);

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
	public java.util.List<MSBFragmentEntry> filterFindByG_MSBFCI_LikeN(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

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
	public MSBFragmentEntry[] filterFindByG_MSBFCI_LikeN_PrevAndNext(
		long msbFragmentEntryId, long groupId, long msbFragmentCollectionId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator)
		throws NoSuchFragmentEntryException;

	/**
	* Removes all the msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	*/
	public void removeByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name);

	/**
	* Returns the number of msb fragment entries where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @return the number of matching msb fragment entries
	*/
	public int countByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name);

	/**
	* Returns the number of msb fragment entries that the user has permission to view where groupId = &#63; and msbFragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param msbFragmentCollectionId the msb fragment collection ID
	* @param name the name
	* @return the number of matching msb fragment entries that the user has permission to view
	*/
	public int filterCountByG_MSBFCI_LikeN(long groupId,
		long msbFragmentCollectionId, java.lang.String name);

	/**
	* Caches the msb fragment entry in the entity cache if it is enabled.
	*
	* @param msbFragmentEntry the msb fragment entry
	*/
	public void cacheResult(MSBFragmentEntry msbFragmentEntry);

	/**
	* Caches the msb fragment entries in the entity cache if it is enabled.
	*
	* @param msbFragmentEntries the msb fragment entries
	*/
	public void cacheResult(java.util.List<MSBFragmentEntry> msbFragmentEntries);

	/**
	* Creates a new msb fragment entry with the primary key. Does not add the msb fragment entry to the database.
	*
	* @param msbFragmentEntryId the primary key for the new msb fragment entry
	* @return the new msb fragment entry
	*/
	public MSBFragmentEntry create(long msbFragmentEntryId);

	/**
	* Removes the msb fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentEntryId the primary key of the msb fragment entry
	* @return the msb fragment entry that was removed
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public MSBFragmentEntry remove(long msbFragmentEntryId)
		throws NoSuchFragmentEntryException;

	public MSBFragmentEntry updateImpl(MSBFragmentEntry msbFragmentEntry);

	/**
	* Returns the msb fragment entry with the primary key or throws a {@link NoSuchFragmentEntryException} if it could not be found.
	*
	* @param msbFragmentEntryId the primary key of the msb fragment entry
	* @return the msb fragment entry
	* @throws NoSuchFragmentEntryException if a msb fragment entry with the primary key could not be found
	*/
	public MSBFragmentEntry findByPrimaryKey(long msbFragmentEntryId)
		throws NoSuchFragmentEntryException;

	/**
	* Returns the msb fragment entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param msbFragmentEntryId the primary key of the msb fragment entry
	* @return the msb fragment entry, or <code>null</code> if a msb fragment entry with the primary key could not be found
	*/
	public MSBFragmentEntry fetchByPrimaryKey(long msbFragmentEntryId);

	@Override
	public java.util.Map<java.io.Serializable, MSBFragmentEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the msb fragment entries.
	*
	* @return the msb fragment entries
	*/
	public java.util.List<MSBFragmentEntry> findAll();

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
	public java.util.List<MSBFragmentEntry> findAll(int start, int end);

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
	public java.util.List<MSBFragmentEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator);

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
	public java.util.List<MSBFragmentEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MSBFragmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the msb fragment entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of msb fragment entries.
	*
	* @return the number of msb fragment entries
	*/
	public int countAll();
}