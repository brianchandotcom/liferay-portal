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

import com.liferay.portal.model.UserTrackerPath;

/**
 * The persistence interface for the user tracker path service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPathPersistenceImpl
 * @see UserTrackerPathUtil
 * @generated
 */
@ProviderType
public interface UserTrackerPathPersistence extends BasePersistence<UserTrackerPath> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserTrackerPathUtil} to access the user tracker path persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the user tracker paths where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @return the matching user tracker paths
	*/
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findByUserTrackerId(
		long userTrackerId);

	/**
	* Returns a range of all the user tracker paths where userTrackerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userTrackerId the user tracker ID
	* @param start the lower bound of the range of user tracker paths
	* @param end the upper bound of the range of user tracker paths (not inclusive)
	* @return the range of matching user tracker paths
	*/
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end);

	/**
	* Returns an ordered range of all the user tracker paths where userTrackerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userTrackerId the user tracker ID
	* @param start the lower bound of the range of user tracker paths
	* @param end the upper bound of the range of user tracker paths (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user tracker paths
	*/
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first user tracker path in the ordered set where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker path
	* @throws com.liferay.portal.NoSuchUserTrackerPathException if a matching user tracker path could not be found
	*/
	public com.liferay.portal.model.UserTrackerPath findByUserTrackerId_First(
		long userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerPathException;

	/**
	* Returns the first user tracker path in the ordered set where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user tracker path, or <code>null</code> if a matching user tracker path could not be found
	*/
	public com.liferay.portal.model.UserTrackerPath fetchByUserTrackerId_First(
		long userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last user tracker path in the ordered set where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker path
	* @throws com.liferay.portal.NoSuchUserTrackerPathException if a matching user tracker path could not be found
	*/
	public com.liferay.portal.model.UserTrackerPath findByUserTrackerId_Last(
		long userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerPathException;

	/**
	* Returns the last user tracker path in the ordered set where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user tracker path, or <code>null</code> if a matching user tracker path could not be found
	*/
	public com.liferay.portal.model.UserTrackerPath fetchByUserTrackerId_Last(
		long userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user tracker paths before and after the current user tracker path in the ordered set where userTrackerId = &#63;.
	*
	* @param userTrackerPathId the primary key of the current user tracker path
	* @param userTrackerId the user tracker ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user tracker path
	* @throws com.liferay.portal.NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	*/
	public com.liferay.portal.model.UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		long userTrackerPathId, long userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserTrackerPathException;

	/**
	* Removes all the user tracker paths where userTrackerId = &#63; from the database.
	*
	* @param userTrackerId the user tracker ID
	*/
	public void removeByUserTrackerId(long userTrackerId);

	/**
	* Returns the number of user tracker paths where userTrackerId = &#63;.
	*
	* @param userTrackerId the user tracker ID
	* @return the number of matching user tracker paths
	*/
	public int countByUserTrackerId(long userTrackerId);

	/**
	* Caches the user tracker path in the entity cache if it is enabled.
	*
	* @param userTrackerPath the user tracker path
	*/
	public void cacheResult(
		com.liferay.portal.model.UserTrackerPath userTrackerPath);

	/**
	* Caches the user tracker paths in the entity cache if it is enabled.
	*
	* @param userTrackerPaths the user tracker paths
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserTrackerPath> userTrackerPaths);

	/**
	* Creates a new user tracker path with the primary key. Does not add the user tracker path to the database.
	*
	* @param userTrackerPathId the primary key for the new user tracker path
	* @return the new user tracker path
	*/
	public com.liferay.portal.model.UserTrackerPath create(
		long userTrackerPathId);

	/**
	* Removes the user tracker path with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userTrackerPathId the primary key of the user tracker path
	* @return the user tracker path that was removed
	* @throws com.liferay.portal.NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	*/
	public com.liferay.portal.model.UserTrackerPath remove(
		long userTrackerPathId)
		throws com.liferay.portal.NoSuchUserTrackerPathException;

	public com.liferay.portal.model.UserTrackerPath updateImpl(
		com.liferay.portal.model.UserTrackerPath userTrackerPath);

	/**
	* Returns the user tracker path with the primary key or throws a {@link com.liferay.portal.NoSuchUserTrackerPathException} if it could not be found.
	*
	* @param userTrackerPathId the primary key of the user tracker path
	* @return the user tracker path
	* @throws com.liferay.portal.NoSuchUserTrackerPathException if a user tracker path with the primary key could not be found
	*/
	public com.liferay.portal.model.UserTrackerPath findByPrimaryKey(
		long userTrackerPathId)
		throws com.liferay.portal.NoSuchUserTrackerPathException;

	/**
	* Returns the user tracker path with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userTrackerPathId the primary key of the user tracker path
	* @return the user tracker path, or <code>null</code> if a user tracker path with the primary key could not be found
	*/
	public com.liferay.portal.model.UserTrackerPath fetchByPrimaryKey(
		long userTrackerPathId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.UserTrackerPath> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the user tracker paths.
	*
	* @return the user tracker paths
	*/
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findAll();

	/**
	* Returns a range of all the user tracker paths.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user tracker paths
	* @param end the upper bound of the range of user tracker paths (not inclusive)
	* @return the range of user tracker paths
	*/
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the user tracker paths.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserTrackerPathModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user tracker paths
	* @param end the upper bound of the range of user tracker paths (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user tracker paths
	*/
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the user tracker paths from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of user tracker paths.
	*
	* @return the number of user tracker paths
	*/
	public int countAll();
}