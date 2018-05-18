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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException;
import com.liferay.portal.tools.service.builder.test.model.LVEntry;

/**
 * The persistence interface for the lv entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryPersistenceImpl
 * @see LVEntryUtil
 * @generated
 */
@ProviderType
public interface LVEntryPersistence extends BasePersistence<LVEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LVEntryUtil} to access the lv entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the lv entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching lv entries
	*/
	public java.util.List<LVEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the lv entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @return the range of matching lv entries
	*/
	public java.util.List<LVEntry> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the lv entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entries
	*/
	public java.util.List<LVEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry> orderByComparator);

	/**
	* Returns an ordered range of all the lv entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entries
	*/
	public java.util.List<LVEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first lv entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry
	* @throws NoSuchLVEntryException if a matching lv entry could not be found
	*/
	public LVEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException;

	/**
	* Returns the first lv entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public LVEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry> orderByComparator);

	/**
	* Returns the last lv entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry
	* @throws NoSuchLVEntryException if a matching lv entry could not be found
	*/
	public LVEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException;

	/**
	* Returns the last lv entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public LVEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry> orderByComparator);

	/**
	* Returns the lv entries before and after the current lv entry in the ordered set where groupId = &#63;.
	*
	* @param lvEntryId the primary key of the current lv entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lv entry
	* @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	*/
	public LVEntry[] findByGroupId_PrevAndNext(long lvEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException;

	/**
	* Removes all the lv entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of lv entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching lv entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the lv entry where headId = &#63; or throws a {@link NoSuchLVEntryException} if it could not be found.
	*
	* @param headId the head ID
	* @return the matching lv entry
	* @throws NoSuchLVEntryException if a matching lv entry could not be found
	*/
	public LVEntry findByHeadId(long headId) throws NoSuchLVEntryException;

	/**
	* Returns the lv entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param headId the head ID
	* @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public LVEntry fetchByHeadId(long headId);

	/**
	* Returns the lv entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param headId the head ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	public LVEntry fetchByHeadId(long headId, boolean retrieveFromCache);

	/**
	* Removes the lv entry where headId = &#63; from the database.
	*
	* @param headId the head ID
	* @return the lv entry that was removed
	*/
	public LVEntry removeByHeadId(long headId) throws NoSuchLVEntryException;

	/**
	* Returns the number of lv entries where headId = &#63;.
	*
	* @param headId the head ID
	* @return the number of matching lv entries
	*/
	public int countByHeadId(long headId);

	/**
	* Caches the lv entry in the entity cache if it is enabled.
	*
	* @param lvEntry the lv entry
	*/
	public void cacheResult(LVEntry lvEntry);

	/**
	* Caches the lv entries in the entity cache if it is enabled.
	*
	* @param lvEntries the lv entries
	*/
	public void cacheResult(java.util.List<LVEntry> lvEntries);

	/**
	* Creates a new lv entry with the primary key. Does not add the lv entry to the database.
	*
	* @param lvEntryId the primary key for the new lv entry
	* @return the new lv entry
	*/
	public LVEntry create(long lvEntryId);

	/**
	* Removes the lv entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param lvEntryId the primary key of the lv entry
	* @return the lv entry that was removed
	* @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	*/
	public LVEntry remove(long lvEntryId) throws NoSuchLVEntryException;

	public LVEntry updateImpl(LVEntry lvEntry);

	/**
	* Returns the lv entry with the primary key or throws a {@link NoSuchLVEntryException} if it could not be found.
	*
	* @param lvEntryId the primary key of the lv entry
	* @return the lv entry
	* @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	*/
	public LVEntry findByPrimaryKey(long lvEntryId)
		throws NoSuchLVEntryException;

	/**
	* Returns the lv entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param lvEntryId the primary key of the lv entry
	* @return the lv entry, or <code>null</code> if a lv entry with the primary key could not be found
	*/
	public LVEntry fetchByPrimaryKey(long lvEntryId);

	@Override
	public java.util.Map<java.io.Serializable, LVEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the lv entries.
	*
	* @return the lv entries
	*/
	public java.util.List<LVEntry> findAll();

	/**
	* Returns a range of all the lv entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @return the range of lv entries
	*/
	public java.util.List<LVEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the lv entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of lv entries
	*/
	public java.util.List<LVEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry> orderByComparator);

	/**
	* Returns an ordered range of all the lv entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of lv entries
	*/
	public java.util.List<LVEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the lv entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of lv entries.
	*
	* @return the number of lv entries
	*/
	public int countAll();
}