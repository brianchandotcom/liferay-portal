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

import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the fragment entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.fragment.service.persistence.impl.FragmentEntryPersistenceImpl
 * @see FragmentEntryUtil
 * @generated
 */
@ProviderType
public interface FragmentEntryPersistence extends BasePersistence<FragmentEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentEntryUtil} to access the fragment entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the fragment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching fragment entries
	*/
	public java.util.List<FragmentEntry> findByGroupId(long groupId);

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
	public java.util.List<FragmentEntry> findByGroupId(long groupId, int start,
		int end);

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
	public java.util.List<FragmentEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public java.util.List<FragmentEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public FragmentEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public FragmentEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public FragmentEntry[] findByGroupId_PrevAndNext(long fragmentEntryId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching fragment entries that the user has permission to view
	*/
	public java.util.List<FragmentEntry> filterFindByGroupId(long groupId);

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
	public java.util.List<FragmentEntry> filterFindByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<FragmentEntry> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set of fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public FragmentEntry[] filterFindByGroupId_PrevAndNext(
		long fragmentEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the fragment entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of fragment entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching fragment entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of fragment entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching fragment entries that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the fragment entries where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @return the matching fragment entries
	*/
	public java.util.List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId);

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
	public java.util.List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end);

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
	public java.util.List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public java.util.List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public FragmentEntry findByFragmentCollectionId_First(
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByFragmentCollectionId_First(
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

	/**
	* Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public FragmentEntry findByFragmentCollectionId_Last(
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByFragmentCollectionId_Last(
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

	/**
	* Returns the fragment entries before and after the current fragment entry in the ordered set where fragmentCollectionId = &#63;.
	*
	* @param fragmentEntryId the primary key of the current fragment entry
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public FragmentEntry[] findByFragmentCollectionId_PrevAndNext(
		long fragmentEntryId, long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the fragment entries where fragmentCollectionId = &#63; from the database.
	*
	* @param fragmentCollectionId the fragment collection ID
	*/
	public void removeByFragmentCollectionId(long fragmentCollectionId);

	/**
	* Returns the number of fragment entries where fragmentCollectionId = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @return the number of matching fragment entries
	*/
	public int countByFragmentCollectionId(long fragmentCollectionId);

	/**
	* Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @return the matching fragment entries
	*/
	public java.util.List<FragmentEntry> findByG_FCI(long groupId,
		long fragmentCollectionId);

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
	public java.util.List<FragmentEntry> findByG_FCI(long groupId,
		long fragmentCollectionId, int start, int end);

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
	public java.util.List<FragmentEntry> findByG_FCI(long groupId,
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public java.util.List<FragmentEntry> findByG_FCI(long groupId,
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public FragmentEntry findByG_FCI_First(long groupId,
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByG_FCI_First(long groupId,
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public FragmentEntry findByG_FCI_Last(long groupId,
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByG_FCI_Last(long groupId,
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public FragmentEntry[] findByG_FCI_PrevAndNext(long fragmentEntryId,
		long groupId, long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @return the matching fragment entries that the user has permission to view
	*/
	public java.util.List<FragmentEntry> filterFindByG_FCI(long groupId,
		long fragmentCollectionId);

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
	public java.util.List<FragmentEntry> filterFindByG_FCI(long groupId,
		long fragmentCollectionId, int start, int end);

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
	public java.util.List<FragmentEntry> filterFindByG_FCI(long groupId,
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public FragmentEntry[] filterFindByG_FCI_PrevAndNext(long fragmentEntryId,
		long groupId, long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	*/
	public void removeByG_FCI(long groupId, long fragmentCollectionId);

	/**
	* Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @return the number of matching fragment entries
	*/
	public int countByG_FCI(long groupId, long fragmentCollectionId);

	/**
	* Returns the number of fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @return the number of matching fragment entries that the user has permission to view
	*/
	public int filterCountByG_FCI(long groupId, long fragmentCollectionId);

	/**
	* Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param fragmentEntryKey the fragment entry key
	* @return the matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public FragmentEntry findByG_FEK(long groupId,
		java.lang.String fragmentEntryKey) throws NoSuchEntryException;

	/**
	* Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param fragmentEntryKey the fragment entry key
	* @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByG_FEK(long groupId,
		java.lang.String fragmentEntryKey);

	/**
	* Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param fragmentEntryKey the fragment entry key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByG_FEK(long groupId,
		java.lang.String fragmentEntryKey, boolean retrieveFromCache);

	/**
	* Removes the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fragmentEntryKey the fragment entry key
	* @return the fragment entry that was removed
	*/
	public FragmentEntry removeByG_FEK(long groupId,
		java.lang.String fragmentEntryKey) throws NoSuchEntryException;

	/**
	* Returns the number of fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryKey the fragment entry key
	* @return the number of matching fragment entries
	*/
	public int countByG_FEK(long groupId, java.lang.String fragmentEntryKey);

	/**
	* Returns all the fragment entries where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @return the matching fragment entries
	*/
	public java.util.List<FragmentEntry> findByFCI_S(
		long fragmentCollectionId, int status);

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
	public java.util.List<FragmentEntry> findByFCI_S(
		long fragmentCollectionId, int status, int start, int end);

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
	public java.util.List<FragmentEntry> findByFCI_S(
		long fragmentCollectionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public java.util.List<FragmentEntry> findByFCI_S(
		long fragmentCollectionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public FragmentEntry findByFCI_S_First(long fragmentCollectionId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByFCI_S_First(long fragmentCollectionId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

	/**
	* Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry
	* @throws NoSuchEntryException if a matching fragment entry could not be found
	*/
	public FragmentEntry findByFCI_S_Last(long fragmentCollectionId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByFCI_S_Last(long fragmentCollectionId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public FragmentEntry[] findByFCI_S_PrevAndNext(long fragmentEntryId,
		long fragmentCollectionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the fragment entries where fragmentCollectionId = &#63; and status = &#63; from the database.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	*/
	public void removeByFCI_S(long fragmentCollectionId, int status);

	/**
	* Returns the number of fragment entries where fragmentCollectionId = &#63; and status = &#63;.
	*
	* @param fragmentCollectionId the fragment collection ID
	* @param status the status
	* @return the number of matching fragment entries
	*/
	public int countByFCI_S(long fragmentCollectionId, int status);

	/**
	* Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @return the matching fragment entries
	*/
	public java.util.List<FragmentEntry> findByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name);

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
	public java.util.List<FragmentEntry> findByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end);

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
	public java.util.List<FragmentEntry> findByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public java.util.List<FragmentEntry> findByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache);

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
	public FragmentEntry findByG_FCI_LikeN_First(long groupId,
		long fragmentCollectionId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByG_FCI_LikeN_First(long groupId,
		long fragmentCollectionId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public FragmentEntry findByG_FCI_LikeN_Last(long groupId,
		long fragmentCollectionId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	*/
	public FragmentEntry fetchByG_FCI_LikeN_Last(long groupId,
		long fragmentCollectionId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public FragmentEntry[] findByG_FCI_LikeN_PrevAndNext(long fragmentEntryId,
		long groupId, long fragmentCollectionId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @return the matching fragment entries that the user has permission to view
	*/
	public java.util.List<FragmentEntry> filterFindByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name);

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
	public java.util.List<FragmentEntry> filterFindByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end);

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
	public java.util.List<FragmentEntry> filterFindByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public FragmentEntry[] filterFindByG_FCI_LikeN_PrevAndNext(
		long fragmentEntryId, long groupId, long fragmentCollectionId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	*/
	public void removeByG_FCI_LikeN(long groupId, long fragmentCollectionId,
		java.lang.String name);

	/**
	* Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @return the number of matching fragment entries
	*/
	public int countByG_FCI_LikeN(long groupId, long fragmentCollectionId,
		java.lang.String name);

	/**
	* Returns the number of fragment entries that the user has permission to view where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param fragmentCollectionId the fragment collection ID
	* @param name the name
	* @return the number of matching fragment entries that the user has permission to view
	*/
	public int filterCountByG_FCI_LikeN(long groupId,
		long fragmentCollectionId, java.lang.String name);

	/**
	* Caches the fragment entry in the entity cache if it is enabled.
	*
	* @param fragmentEntry the fragment entry
	*/
	public void cacheResult(FragmentEntry fragmentEntry);

	/**
	* Caches the fragment entries in the entity cache if it is enabled.
	*
	* @param fragmentEntries the fragment entries
	*/
	public void cacheResult(java.util.List<FragmentEntry> fragmentEntries);

	/**
	* Creates a new fragment entry with the primary key. Does not add the fragment entry to the database.
	*
	* @param fragmentEntryId the primary key for the new fragment entry
	* @return the new fragment entry
	*/
	public FragmentEntry create(long fragmentEntryId);

	/**
	* Removes the fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryId the primary key of the fragment entry
	* @return the fragment entry that was removed
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public FragmentEntry remove(long fragmentEntryId)
		throws NoSuchEntryException;

	public FragmentEntry updateImpl(FragmentEntry fragmentEntry);

	/**
	* Returns the fragment entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param fragmentEntryId the primary key of the fragment entry
	* @return the fragment entry
	* @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	*/
	public FragmentEntry findByPrimaryKey(long fragmentEntryId)
		throws NoSuchEntryException;

	/**
	* Returns the fragment entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fragmentEntryId the primary key of the fragment entry
	* @return the fragment entry, or <code>null</code> if a fragment entry with the primary key could not be found
	*/
	public FragmentEntry fetchByPrimaryKey(long fragmentEntryId);

	@Override
	public java.util.Map<java.io.Serializable, FragmentEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the fragment entries.
	*
	* @return the fragment entries
	*/
	public java.util.List<FragmentEntry> findAll();

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
	public java.util.List<FragmentEntry> findAll(int start, int end);

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
	public java.util.List<FragmentEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator);

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
	public java.util.List<FragmentEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the fragment entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of fragment entries.
	*
	* @return the number of fragment entries
	*/
	public int countAll();
}