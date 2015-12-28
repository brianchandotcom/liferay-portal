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
 * Provides a wrapper for {@link VirtualHostLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostLocalService
 * @generated
 */
@ProviderType
public class VirtualHostLocalServiceWrapper implements VirtualHostLocalService,
	ServiceWrapper<VirtualHostLocalService> {
	public VirtualHostLocalServiceWrapper(
		VirtualHostLocalService virtualHostLocalService) {
		_virtualHostLocalService = virtualHostLocalService;
	}

	/**
	* Adds the virtual host to the database. Also notifies the appropriate model listeners.
	*
	* @param virtualHost the virtual host
	* @return the virtual host that was added
	*/
	@Override
	public com.liferay.portal.model.VirtualHost addVirtualHost(
		com.liferay.portal.model.VirtualHost virtualHost) {
		return _virtualHostLocalService.addVirtualHost(virtualHost);
	}

	/**
	* Creates a new virtual host with the primary key. Does not add the virtual host to the database.
	*
	* @param virtualHostId the primary key for the new virtual host
	* @return the new virtual host
	*/
	@Override
	public com.liferay.portal.model.VirtualHost createVirtualHost(
		long virtualHostId) {
		return _virtualHostLocalService.createVirtualHost(virtualHostId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _virtualHostLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the virtual host from the database. Also notifies the appropriate model listeners.
	*
	* @param virtualHost the virtual host
	* @return the virtual host that was removed
	*/
	@Override
	public com.liferay.portal.model.VirtualHost deleteVirtualHost(
		com.liferay.portal.model.VirtualHost virtualHost) {
		return _virtualHostLocalService.deleteVirtualHost(virtualHost);
	}

	/**
	* Deletes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param virtualHostId the primary key of the virtual host
	* @return the virtual host that was removed
	* @throws PortalException if a virtual host with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.VirtualHost deleteVirtualHost(
		long virtualHostId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _virtualHostLocalService.deleteVirtualHost(virtualHostId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _virtualHostLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _virtualHostLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _virtualHostLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _virtualHostLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _virtualHostLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _virtualHostLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.VirtualHost fetchVirtualHost(
		long companyId, long layoutSetId) {
		return _virtualHostLocalService.fetchVirtualHost(companyId, layoutSetId);
	}

	@Override
	public com.liferay.portal.model.VirtualHost fetchVirtualHost(
		java.lang.String hostname) {
		return _virtualHostLocalService.fetchVirtualHost(hostname);
	}

	@Override
	public com.liferay.portal.model.VirtualHost fetchVirtualHost(
		long virtualHostId) {
		return _virtualHostLocalService.fetchVirtualHost(virtualHostId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _virtualHostLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _virtualHostLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _virtualHostLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _virtualHostLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.model.VirtualHost getVirtualHost(long companyId,
		long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _virtualHostLocalService.getVirtualHost(companyId, layoutSetId);
	}

	@Override
	public com.liferay.portal.model.VirtualHost getVirtualHost(
		java.lang.String hostname)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _virtualHostLocalService.getVirtualHost(hostname);
	}

	/**
	* Returns the virtual host with the primary key.
	*
	* @param virtualHostId the primary key of the virtual host
	* @return the virtual host
	* @throws PortalException if a virtual host with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.VirtualHost getVirtualHost(
		long virtualHostId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _virtualHostLocalService.getVirtualHost(virtualHostId);
	}

	/**
	* Returns a range of all the virtual hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of virtual hosts
	* @param end the upper bound of the range of virtual hosts (not inclusive)
	* @return the range of virtual hosts
	*/
	@Override
	public java.util.List<com.liferay.portal.model.VirtualHost> getVirtualHosts(
		int start, int end) {
		return _virtualHostLocalService.getVirtualHosts(start, end);
	}

	/**
	* Returns the number of virtual hosts.
	*
	* @return the number of virtual hosts
	*/
	@Override
	public int getVirtualHostsCount() {
		return _virtualHostLocalService.getVirtualHostsCount();
	}

	@Override
	public com.liferay.portal.model.VirtualHost updateVirtualHost(
		long companyId, long layoutSetId, java.lang.String hostname) {
		return _virtualHostLocalService.updateVirtualHost(companyId,
			layoutSetId, hostname);
	}

	/**
	* Updates the virtual host in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param virtualHost the virtual host
	* @return the virtual host that was updated
	*/
	@Override
	public com.liferay.portal.model.VirtualHost updateVirtualHost(
		com.liferay.portal.model.VirtualHost virtualHost) {
		return _virtualHostLocalService.updateVirtualHost(virtualHost);
	}

	@Override
	public VirtualHostLocalService getWrappedService() {
		return _virtualHostLocalService;
	}

	@Override
	public void setWrappedService(
		VirtualHostLocalService virtualHostLocalService) {
		_virtualHostLocalService = virtualHostLocalService;
	}

	private VirtualHostLocalService _virtualHostLocalService;
}