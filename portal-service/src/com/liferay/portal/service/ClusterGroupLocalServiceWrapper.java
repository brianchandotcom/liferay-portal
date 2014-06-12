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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link ClusterGroupLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ClusterGroupLocalService
 * @generated
 */
@ProviderType
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
	*/
	@Override
	public com.liferay.portal.model.ClusterGroup addClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup) {
		return _clusterGroupLocalService.addClusterGroup(clusterGroup);
	}

	/**
	* Creates a new cluster group with the primary key. Does not add the cluster group to the database.
	*
	* @param clusterGroupId the primary key for the new cluster group
	* @return the new cluster group
	*/
	@Override
	public com.liferay.portal.model.ClusterGroup createClusterGroup(
		long clusterGroupId) {
		return _clusterGroupLocalService.createClusterGroup(clusterGroupId);
	}

	/**
	* Deletes the cluster group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param clusterGroupId the primary key of the cluster group
	* @return the cluster group that was removed
	* @throws PortalException if a cluster group with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.ClusterGroup deleteClusterGroup(
		long clusterGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _clusterGroupLocalService.deleteClusterGroup(clusterGroupId);
	}

	/**
	* Deletes the cluster group from the database. Also notifies the appropriate model listeners.
	*
	* @param clusterGroup the cluster group
	* @return the cluster group that was removed
	*/
	@Override
	public com.liferay.portal.model.ClusterGroup deleteClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup) {
		return _clusterGroupLocalService.deleteClusterGroup(clusterGroup);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _clusterGroupLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _clusterGroupLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ClusterGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _clusterGroupLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ClusterGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _clusterGroupLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _clusterGroupLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _clusterGroupLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.ClusterGroup fetchClusterGroup(
		long clusterGroupId) {
		return _clusterGroupLocalService.fetchClusterGroup(clusterGroupId);
	}

	/**
	* Returns the cluster group with the primary key.
	*
	* @param clusterGroupId the primary key of the cluster group
	* @return the cluster group
	* @throws PortalException if a cluster group with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.ClusterGroup getClusterGroup(
		long clusterGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _clusterGroupLocalService.getClusterGroup(clusterGroupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _clusterGroupLocalService.getActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _clusterGroupLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _clusterGroupLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the cluster groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ClusterGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cluster groups
	* @param end the upper bound of the range of cluster groups (not inclusive)
	* @return the range of cluster groups
	*/
	@Override
	public java.util.List<com.liferay.portal.model.ClusterGroup> getClusterGroups(
		int start, int end) {
		return _clusterGroupLocalService.getClusterGroups(start, end);
	}

	/**
	* Returns the number of cluster groups.
	*
	* @return the number of cluster groups
	*/
	@Override
	public int getClusterGroupsCount() {
		return _clusterGroupLocalService.getClusterGroupsCount();
	}

	/**
	* Updates the cluster group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param clusterGroup the cluster group
	* @return the cluster group that was updated
	*/
	@Override
	public com.liferay.portal.model.ClusterGroup updateClusterGroup(
		com.liferay.portal.model.ClusterGroup clusterGroup) {
		return _clusterGroupLocalService.updateClusterGroup(clusterGroup);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _clusterGroupLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_clusterGroupLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.ClusterGroup addClusterGroup(
		java.lang.String name, java.util.List<java.lang.String> clusterNodeIds) {
		return _clusterGroupLocalService.addClusterGroup(name, clusterNodeIds);
	}

	@Override
	public com.liferay.portal.model.ClusterGroup addWholeClusterGroup(
		java.lang.String name) {
		return _clusterGroupLocalService.addWholeClusterGroup(name);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ClusterGroupLocalService getWrappedClusterGroupLocalService() {
		return _clusterGroupLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedClusterGroupLocalService(
		ClusterGroupLocalService clusterGroupLocalService) {
		_clusterGroupLocalService = clusterGroupLocalService;
	}

	@Override
	public ClusterGroupLocalService getWrappedService() {
		return _clusterGroupLocalService;
	}

	@Override
	public void setWrappedService(
		ClusterGroupLocalService clusterGroupLocalService) {
		_clusterGroupLocalService = clusterGroupLocalService;
	}

	private ClusterGroupLocalService _clusterGroupLocalService;
}