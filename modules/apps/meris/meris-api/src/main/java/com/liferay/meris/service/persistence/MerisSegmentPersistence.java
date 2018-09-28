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

package com.liferay.meris.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.meris.exception.NoSuchSegmentException;
import com.liferay.meris.model.MerisSegment;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the meris segment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see com.liferay.meris.service.persistence.impl.MerisSegmentPersistenceImpl
 * @see MerisSegmentUtil
 * @generated
 */
@ProviderType
public interface MerisSegmentPersistence extends BasePersistence<MerisSegment> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MerisSegmentUtil} to access the meris segment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the meris segments where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching meris segments
	*/
	public java.util.List<MerisSegment> findByGroupId(long groupId);

	/**
	* Returns a range of all the meris segments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @return the range of matching meris segments
	*/
	public java.util.List<MerisSegment> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the meris segments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching meris segments
	*/
	public java.util.List<MerisSegment> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator);

	/**
	* Returns an ordered range of all the meris segments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching meris segments
	*/
	public java.util.List<MerisSegment> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first meris segment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment
	* @throws NoSuchSegmentException if a matching meris segment could not be found
	*/
	public MerisSegment findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException;

	/**
	* Returns the first meris segment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public MerisSegment fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator);

	/**
	* Returns the last meris segment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment
	* @throws NoSuchSegmentException if a matching meris segment could not be found
	*/
	public MerisSegment findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException;

	/**
	* Returns the last meris segment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public MerisSegment fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator);

	/**
	* Returns the meris segments before and after the current meris segment in the ordered set where groupId = &#63;.
	*
	* @param merisSegmentId the primary key of the current meris segment
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next meris segment
	* @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	*/
	public MerisSegment[] findByGroupId_PrevAndNext(long merisSegmentId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException;

	/**
	* Removes all the meris segments where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of meris segments where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching meris segments
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the meris segment where groupId = &#63; and key = &#63; or throws a {@link NoSuchSegmentException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching meris segment
	* @throws NoSuchSegmentException if a matching meris segment could not be found
	*/
	public MerisSegment findByG_K(long groupId, String key)
		throws NoSuchSegmentException;

	/**
	* Returns the meris segment where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public MerisSegment fetchByG_K(long groupId, String key);

	/**
	* Returns the meris segment where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public MerisSegment fetchByG_K(long groupId, String key,
		boolean retrieveFromCache);

	/**
	* Removes the meris segment where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the meris segment that was removed
	*/
	public MerisSegment removeByG_K(long groupId, String key)
		throws NoSuchSegmentException;

	/**
	* Returns the number of meris segments where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching meris segments
	*/
	public int countByG_K(long groupId, String key);

	/**
	* Returns all the meris segments where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching meris segments
	*/
	public java.util.List<MerisSegment> findByG_A(long groupId, boolean active);

	/**
	* Returns a range of all the meris segments where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @return the range of matching meris segments
	*/
	public java.util.List<MerisSegment> findByG_A(long groupId, boolean active,
		int start, int end);

	/**
	* Returns an ordered range of all the meris segments where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching meris segments
	*/
	public java.util.List<MerisSegment> findByG_A(long groupId, boolean active,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator);

	/**
	* Returns an ordered range of all the meris segments where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching meris segments
	*/
	public java.util.List<MerisSegment> findByG_A(long groupId, boolean active,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first meris segment in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment
	* @throws NoSuchSegmentException if a matching meris segment could not be found
	*/
	public MerisSegment findByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException;

	/**
	* Returns the first meris segment in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public MerisSegment fetchByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator);

	/**
	* Returns the last meris segment in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment
	* @throws NoSuchSegmentException if a matching meris segment could not be found
	*/
	public MerisSegment findByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException;

	/**
	* Returns the last meris segment in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public MerisSegment fetchByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator);

	/**
	* Returns the meris segments before and after the current meris segment in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param merisSegmentId the primary key of the current meris segment
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next meris segment
	* @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	*/
	public MerisSegment[] findByG_A_PrevAndNext(long merisSegmentId,
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator)
		throws NoSuchSegmentException;

	/**
	* Removes all the meris segments where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public void removeByG_A(long groupId, boolean active);

	/**
	* Returns the number of meris segments where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching meris segments
	*/
	public int countByG_A(long groupId, boolean active);

	/**
	* Caches the meris segment in the entity cache if it is enabled.
	*
	* @param merisSegment the meris segment
	*/
	public void cacheResult(MerisSegment merisSegment);

	/**
	* Caches the meris segments in the entity cache if it is enabled.
	*
	* @param merisSegments the meris segments
	*/
	public void cacheResult(java.util.List<MerisSegment> merisSegments);

	/**
	* Creates a new meris segment with the primary key. Does not add the meris segment to the database.
	*
	* @param merisSegmentId the primary key for the new meris segment
	* @return the new meris segment
	*/
	public MerisSegment create(long merisSegmentId);

	/**
	* Removes the meris segment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param merisSegmentId the primary key of the meris segment
	* @return the meris segment that was removed
	* @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	*/
	public MerisSegment remove(long merisSegmentId)
		throws NoSuchSegmentException;

	public MerisSegment updateImpl(MerisSegment merisSegment);

	/**
	* Returns the meris segment with the primary key or throws a {@link NoSuchSegmentException} if it could not be found.
	*
	* @param merisSegmentId the primary key of the meris segment
	* @return the meris segment
	* @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	*/
	public MerisSegment findByPrimaryKey(long merisSegmentId)
		throws NoSuchSegmentException;

	/**
	* Returns the meris segment with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param merisSegmentId the primary key of the meris segment
	* @return the meris segment, or <code>null</code> if a meris segment with the primary key could not be found
	*/
	public MerisSegment fetchByPrimaryKey(long merisSegmentId);

	@Override
	public java.util.Map<java.io.Serializable, MerisSegment> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the meris segments.
	*
	* @return the meris segments
	*/
	public java.util.List<MerisSegment> findAll();

	/**
	* Returns a range of all the meris segments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @return the range of meris segments
	*/
	public java.util.List<MerisSegment> findAll(int start, int end);

	/**
	* Returns an ordered range of all the meris segments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of meris segments
	*/
	public java.util.List<MerisSegment> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator);

	/**
	* Returns an ordered range of all the meris segments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of meris segments
	*/
	public java.util.List<MerisSegment> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the meris segments from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of meris segments.
	*
	* @return the number of meris segments
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}