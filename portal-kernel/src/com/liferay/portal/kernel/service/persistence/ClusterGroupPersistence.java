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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchClusterGroupException;
import com.liferay.portal.kernel.model.ClusterGroup;

/**
 * The persistence interface for the cluster group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClusterGroupUtil
 * @generated
 */
@ProviderType
public interface ClusterGroupPersistence extends BasePersistence<ClusterGroup> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ClusterGroupUtil} to access the cluster group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the cluster group in the entity cache if it is enabled.
	*
	* @param clusterGroup the cluster group
	*/
	public void cacheResult(ClusterGroup clusterGroup);

	/**
	* Caches the cluster groups in the entity cache if it is enabled.
	*
	* @param clusterGroups the cluster groups
	*/
	public void cacheResult(java.util.List<ClusterGroup> clusterGroups);

	/**
	* Creates a new cluster group with the primary key. Does not add the cluster group to the database.
	*
	* @param clusterGroupId the primary key for the new cluster group
	* @return the new cluster group
	*/
	public ClusterGroup create(long clusterGroupId);

	/**
	* Removes the cluster group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param clusterGroupId the primary key of the cluster group
	* @return the cluster group that was removed
	* @throws NoSuchClusterGroupException if a cluster group with the primary key could not be found
	*/
	public ClusterGroup remove(long clusterGroupId)
		throws NoSuchClusterGroupException;

	public ClusterGroup updateImpl(ClusterGroup clusterGroup);

	/**
	* Returns the cluster group with the primary key or throws a <code>NoSuchClusterGroupException</code> if it could not be found.
	*
	* @param clusterGroupId the primary key of the cluster group
	* @return the cluster group
	* @throws NoSuchClusterGroupException if a cluster group with the primary key could not be found
	*/
	public ClusterGroup findByPrimaryKey(long clusterGroupId)
		throws NoSuchClusterGroupException;

	/**
	* Returns the cluster group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param clusterGroupId the primary key of the cluster group
	* @return the cluster group, or <code>null</code> if a cluster group with the primary key could not be found
	*/
	public ClusterGroup fetchByPrimaryKey(long clusterGroupId);

	/**
	* Returns all the cluster groups.
	*
	* @return the cluster groups
	*/
	public java.util.List<ClusterGroup> findAll();

	/**
	* Returns a range of all the cluster groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ClusterGroupModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cluster groups
	* @param end the upper bound of the range of cluster groups (not inclusive)
	* @return the range of cluster groups
	*/
	public java.util.List<ClusterGroup> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cluster groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ClusterGroupModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cluster groups
	* @param end the upper bound of the range of cluster groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cluster groups
	*/
	public java.util.List<ClusterGroup> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClusterGroup> orderByComparator);

	/**
	* Returns an ordered range of all the cluster groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ClusterGroupModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cluster groups
	* @param end the upper bound of the range of cluster groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cluster groups
	*/
	public java.util.List<ClusterGroup> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ClusterGroup> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cluster groups from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cluster groups.
	*
	* @return the number of cluster groups
	*/
	public int countAll();
}