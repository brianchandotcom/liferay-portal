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

package com.liferay.segments.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.segments.exception.NoSuchEntryException;
import com.liferay.segments.model.SegmentsEntry;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the segments entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see com.liferay.segments.service.persistence.impl.SegmentsEntryPersistenceImpl
 * @see SegmentsEntryUtil
 * @generated
 */
@ProviderType
public interface SegmentsEntryPersistence extends BasePersistence<SegmentsEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SegmentsEntryUtil} to access the segments entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, SegmentsEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	* Returns all the segments entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the segments entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry[] findByGroupId_PrevAndNext(long segmentsEntryId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the segments entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry[] filterFindByGroupId_PrevAndNext(
		long segmentsEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the segments entries that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByGroupId(long[] groupIds);

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByGroupId(long[] groupIds,
		int start, int end);

	/**
	* Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByGroupId(long[] groupIds,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns all the segments entries where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByGroupId(long[] groupIds);

	/**
	* Returns a range of all the segments entries where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByGroupId(long[] groupIds,
		int start, int end);

	/**
	* Returns an ordered range of all the segments entries where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByGroupId(long[] groupIds,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByGroupId(long[] groupIds,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the segments entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of segments entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching segments entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of segments entries where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching segments entries
	*/
	public int countByGroupId(long[] groupIds);

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching segments entries that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching segments entries that the user has permission to view
	*/
	public int filterCountByGroupId(long[] groupIds);

	/**
	* Returns all the segments entries where source = &#63;.
	*
	* @param source the source
	* @return the matching segments entries
	*/
	public java.util.List<SegmentsEntry> findBySource(String source);

	/**
	* Returns a range of all the segments entries where source = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param source the source
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findBySource(String source, int start,
		int end);

	/**
	* Returns an ordered range of all the segments entries where source = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param source the source
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findBySource(String source, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the segments entries where source = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param source the source
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findBySource(String source, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first segments entry in the ordered set where source = &#63;.
	*
	* @param source the source
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findBySource_First(String source,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first segments entry in the ordered set where source = &#63;.
	*
	* @param source the source
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchBySource_First(String source,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the last segments entry in the ordered set where source = &#63;.
	*
	* @param source the source
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findBySource_Last(String source,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last segments entry in the ordered set where source = &#63;.
	*
	* @param source the source
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchBySource_Last(String source,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where source = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param source the source
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry[] findBySource_PrevAndNext(long segmentsEntryId,
		String source,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the segments entries where source = &#63; from the database.
	*
	* @param source the source
	*/
	public void removeBySource(String source);

	/**
	* Returns the number of segments entries where source = &#63;.
	*
	* @param source the source
	* @return the number of matching segments entries
	*/
	public int countBySource(String source);

	/**
	* Returns all the segments entries where type = &#63;.
	*
	* @param type the type
	* @return the matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByType(String type);

	/**
	* Returns a range of all the segments entries where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByType(String type, int start,
		int end);

	/**
	* Returns an ordered range of all the segments entries where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByType(String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the segments entries where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByType(String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first segments entry in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByType_First(String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first segments entry in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByType_First(String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the last segments entry in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByType_Last(String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last segments entry in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByType_Last(String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where type = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry[] findByType_PrevAndNext(long segmentsEntryId,
		String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the segments entries where type = &#63; from the database.
	*
	* @param type the type
	*/
	public void removeByType(String type);

	/**
	* Returns the number of segments entries where type = &#63;.
	*
	* @param type the type
	* @return the number of matching segments entries
	*/
	public int countByType(String type);

	/**
	* Returns all the segments entries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A(long groupId, boolean active);

	/**
	* Returns a range of all the segments entries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A(long groupId,
		boolean active, int start, int end);

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry[] findByG_A_PrevAndNext(long segmentsEntryId,
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A(long groupId,
		boolean active);

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A(long groupId,
		boolean active, int start, int end);

	/**
	* Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry[] filterFindByG_A_PrevAndNext(long segmentsEntryId,
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @return the matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A(long[] groupIds,
		boolean active);

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A(long[] groupIds,
		boolean active, int start, int end);

	/**
	* Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A(long[] groupIds,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns all the segments entries where groupId = any &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @return the matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A(long[] groupIds,
		boolean active);

	/**
	* Returns a range of all the segments entries where groupId = any &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A(long[] groupIds,
		boolean active, int start, int end);

	/**
	* Returns an ordered range of all the segments entries where groupId = any &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A(long[] groupIds,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A(long[] groupIds,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the segments entries where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public void removeByG_A(long groupId, boolean active);

	/**
	* Returns the number of segments entries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching segments entries
	*/
	public int countByG_A(long groupId, boolean active);

	/**
	* Returns the number of segments entries where groupId = any &#63; and active = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @return the number of matching segments entries
	*/
	public int countByG_A(long[] groupIds, boolean active);

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching segments entries that the user has permission to view
	*/
	public int filterCountByG_A(long groupId, boolean active);

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = any &#63; and active = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @return the number of matching segments entries that the user has permission to view
	*/
	public int filterCountByG_A(long[] groupIds, boolean active);

	/**
	* Returns the segments entry where groupId = &#63; and key = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByG_K(long groupId, String key)
		throws NoSuchEntryException;

	/**
	* Returns the segments entry where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByG_K(long groupId, String key);

	/**
	* Returns the segments entry where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByG_K(long groupId, String key,
		boolean retrieveFromCache);

	/**
	* Removes the segments entry where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the segments entry that was removed
	*/
	public SegmentsEntry removeByG_K(long groupId, String key)
		throws NoSuchEntryException;

	/**
	* Returns the number of segments entries where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching segments entries
	*/
	public int countByG_K(long groupId, String key);

	/**
	* Returns all the segments entries where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @return the matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByA_T(boolean active, String type);

	/**
	* Returns a range of all the segments entries where active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByA_T(boolean active, String type,
		int start, int end);

	/**
	* Returns an ordered range of all the segments entries where active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByA_T(boolean active, String type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the segments entries where active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByA_T(boolean active, String type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first segments entry in the ordered set where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByA_T_First(boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first segments entry in the ordered set where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByA_T_First(boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the last segments entry in the ordered set where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByA_T_Last(boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last segments entry in the ordered set where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByA_T_Last(boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where active = &#63; and type = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry[] findByA_T_PrevAndNext(long segmentsEntryId,
		boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the segments entries where active = &#63; and type = &#63; from the database.
	*
	* @param active the active
	* @param type the type
	*/
	public void removeByA_T(boolean active, String type);

	/**
	* Returns the number of segments entries where active = &#63; and type = &#63;.
	*
	* @param active the active
	* @param type the type
	* @return the number of matching segments entries
	*/
	public int countByA_T(boolean active, String type);

	/**
	* Returns all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @return the matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A_T(long groupId,
		boolean active, String type);

	/**
	* Returns a range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A_T(long groupId,
		boolean active, String type, int start, int end);

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A_T(long groupId,
		boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A_T(long groupId,
		boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByG_A_T_First(long groupId, boolean active,
		String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByG_A_T_First(long groupId, boolean active,
		String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry
	* @throws NoSuchEntryException if a matching segments entry could not be found
	*/
	public SegmentsEntry findByG_A_T_Last(long groupId, boolean active,
		String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments entry, or <code>null</code> if a matching segments entry could not be found
	*/
	public SegmentsEntry fetchByG_A_T_Last(long groupId, boolean active,
		String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry[] findByG_A_T_PrevAndNext(long segmentsEntryId,
		long groupId, boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @return the matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A_T(long groupId,
		boolean active, String type);

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A_T(long groupId,
		boolean active, String type, int start, int end);

	/**
	* Returns an ordered range of all the segments entries that the user has permissions to view where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A_T(long groupId,
		boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns the segments entries before and after the current segments entry in the ordered set of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param segmentsEntryId the primary key of the current segments entry
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry[] filterFindByG_A_T_PrevAndNext(long segmentsEntryId,
		long groupId, boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @return the matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A_T(long[] groupIds,
		boolean active, String type);

	/**
	* Returns a range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A_T(long[] groupIds,
		boolean active, String type, int start, int end);

	/**
	* Returns an ordered range of all the segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries that the user has permission to view
	*/
	public java.util.List<SegmentsEntry> filterFindByG_A_T(long[] groupIds,
		boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns all the segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @return the matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A_T(long[] groupIds,
		boolean active, String type);

	/**
	* Returns a range of all the segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A_T(long[] groupIds,
		boolean active, String type, int start, int end);

	/**
	* Returns an ordered range of all the segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A_T(long[] groupIds,
		boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the segments entries where groupId = &#63; and active = &#63; and type = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments entries
	*/
	public java.util.List<SegmentsEntry> findByG_A_T(long[] groupIds,
		boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the segments entries where groupId = &#63; and active = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	*/
	public void removeByG_A_T(long groupId, boolean active, String type);

	/**
	* Returns the number of segments entries where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @return the number of matching segments entries
	*/
	public int countByG_A_T(long groupId, boolean active, String type);

	/**
	* Returns the number of segments entries where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @return the number of matching segments entries
	*/
	public int countByG_A_T(long[] groupIds, boolean active, String type);

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = &#63; and active = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param type the type
	* @return the number of matching segments entries that the user has permission to view
	*/
	public int filterCountByG_A_T(long groupId, boolean active, String type);

	/**
	* Returns the number of segments entries that the user has permission to view where groupId = any &#63; and active = &#63; and type = &#63;.
	*
	* @param groupIds the group IDs
	* @param active the active
	* @param type the type
	* @return the number of matching segments entries that the user has permission to view
	*/
	public int filterCountByG_A_T(long[] groupIds, boolean active, String type);

	/**
	* Caches the segments entry in the entity cache if it is enabled.
	*
	* @param segmentsEntry the segments entry
	*/
	public void cacheResult(SegmentsEntry segmentsEntry);

	/**
	* Caches the segments entries in the entity cache if it is enabled.
	*
	* @param segmentsEntries the segments entries
	*/
	public void cacheResult(java.util.List<SegmentsEntry> segmentsEntries);

	/**
	* Creates a new segments entry with the primary key. Does not add the segments entry to the database.
	*
	* @param segmentsEntryId the primary key for the new segments entry
	* @return the new segments entry
	*/
	public SegmentsEntry create(long segmentsEntryId);

	/**
	* Removes the segments entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param segmentsEntryId the primary key of the segments entry
	* @return the segments entry that was removed
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry remove(long segmentsEntryId)
		throws NoSuchEntryException;

	public SegmentsEntry updateImpl(SegmentsEntry segmentsEntry);

	/**
	* Returns the segments entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param segmentsEntryId the primary key of the segments entry
	* @return the segments entry
	* @throws NoSuchEntryException if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry findByPrimaryKey(long segmentsEntryId)
		throws NoSuchEntryException;

	/**
	* Returns the segments entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param segmentsEntryId the primary key of the segments entry
	* @return the segments entry, or <code>null</code> if a segments entry with the primary key could not be found
	*/
	public SegmentsEntry fetchByPrimaryKey(long segmentsEntryId);

	/**
	* Returns all the segments entries.
	*
	* @return the segments entries
	*/
	public java.util.List<SegmentsEntry> findAll();

	/**
	* Returns a range of all the segments entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @return the range of segments entries
	*/
	public java.util.List<SegmentsEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the segments entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of segments entries
	*/
	public java.util.List<SegmentsEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the segments entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of segments entries
	* @param end the upper bound of the range of segments entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of segments entries
	*/
	public java.util.List<SegmentsEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the segments entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of segments entries.
	*
	* @return the number of segments entries
	*/
	public int countAll();

	@Override
	public Set<String> getBadColumnNames();
}