/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link ClusterGroupLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClusterGroupLocalService
 * @generated
 */
public class ClusterGroupLocalServiceWrapper implements ClusterGroupLocalService,
	ServiceWrapper<ClusterGroupLocalService> {
	public ClusterGroupLocalServiceWrapper(
		ClusterGroupLocalService clusterGroupLocalService) {
		_clusterGroupLocalService = clusterGroupLocalService;
	}

	/**
	* Adds the cluster group to the database. Also notifies the appropriate model listeners.
	*
	* @param clusterGroup the cluster group
	* @return the cluster group that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ClusterGroup addClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.addClusterGroup(clusterGroup);
	}

	/**
	* Creates a new cluster group with the primary key. Does not add the cluster group to the database.
	*
	* @param clusterGroupId the primary key for the new cluster group
	* @return the new cluster group
	*/
	public com.liferay.portal.model.ClusterGroup createClusterGroup(
		long clusterGroupId) {
		return _clusterGroupLocalService.createClusterGroup(clusterGroupId);
	}

	/**
	* Deletes the cluster group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param clusterGroupId the primary key of the cluster group
	* @throws PortalException if a cluster group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteClusterGroup(long clusterGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_clusterGroupLocalService.deleteClusterGroup(clusterGroupId);
	}

	/**
	* Deletes the cluster group from the database. Also notifies the appropriate model listeners.
	*
	* @param clusterGroup the cluster group
	* @throws SystemException if a system exception occurred
	*/
	public void deleteClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		_clusterGroupLocalService.deleteClusterGroup(clusterGroup);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the cluster group with the primary key.
	*
	* @param clusterGroupId the primary key of the cluster group
	* @return the cluster group
	* @throws PortalException if a cluster group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ClusterGroup getClusterGroup(
		long clusterGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.getClusterGroup(clusterGroupId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the cluster groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of cluster groups
	* @param end the upper bound of the range of cluster groups (not inclusive)
	* @return the range of cluster groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ClusterGroup> getClusterGroups(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.getClusterGroups(start, end);
	}

	/**
	* Returns the number of cluster groups.
	*
	* @return the number of cluster groups
	* @throws SystemException if a system exception occurred
	*/
	public int getClusterGroupsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.getClusterGroupsCount();
	}

	/**
	* Updates the cluster group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param clusterGroup the cluster group
	* @return the cluster group that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ClusterGroup updateClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.updateClusterGroup(clusterGroup);
	}

	/**
	* Updates the cluster group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param clusterGroup the cluster group
	* @param merge whether to merge the cluster group with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the cluster group that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ClusterGroup updateClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.updateClusterGroup(clusterGroup, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _clusterGroupLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_clusterGroupLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.ClusterGroup addClusterGroup(
		java.lang.String name, java.util.List<java.lang.String> clusterNodeIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.addClusterGroup(name, clusterNodeIds);
	}

	public com.liferay.portal.model.ClusterGroup addWholeClusterGroup(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _clusterGroupLocalService.addWholeClusterGroup(name);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ClusterGroupLocalService getWrappedClusterGroupLocalService() {
		return _clusterGroupLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedClusterGroupLocalService(
		ClusterGroupLocalService clusterGroupLocalService) {
		_clusterGroupLocalService = clusterGroupLocalService;
	}

	public ClusterGroupLocalService getWrappedService() {
		return _clusterGroupLocalService;
	}

	public void setWrappedService(
		ClusterGroupLocalService clusterGroupLocalService) {
		_clusterGroupLocalService = clusterGroupLocalService;
	}

	private ClusterGroupLocalService _clusterGroupLocalService;
}