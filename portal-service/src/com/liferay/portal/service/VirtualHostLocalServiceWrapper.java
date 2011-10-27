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
 * This class is a wrapper for {@link VirtualHostLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       VirtualHostLocalService
 * @generated
 */
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
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost addVirtualHost(
		com.liferay.portal.model.VirtualHost virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.addVirtualHost(virtualHost);
	}

	/**
	* Creates a new virtual host with the primary key. Does not add the virtual host to the database.
	*
	* @param virtualHostId the primary key for the new virtual host
	* @return the new virtual host
	*/
	public com.liferay.portal.model.VirtualHost createVirtualHost(
		long virtualHostId) {
		return _virtualHostLocalService.createVirtualHost(virtualHostId);
	}

	/**
	* Deletes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param virtualHostId the primary key of the virtual host
	* @throws PortalException if a virtual host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteVirtualHost(long virtualHostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_virtualHostLocalService.deleteVirtualHost(virtualHostId);
	}

	/**
	* Deletes the virtual host from the database. Also notifies the appropriate model listeners.
	*
	* @param virtualHost the virtual host
	* @throws SystemException if a system exception occurred
	*/
	public void deleteVirtualHost(
		com.liferay.portal.model.VirtualHost virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException {
		_virtualHostLocalService.deleteVirtualHost(virtualHost);
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
		return _virtualHostLocalService.dynamicQuery(dynamicQuery);
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
		return _virtualHostLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _virtualHostLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _virtualHostLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the virtual host with the primary key.
	*
	* @param virtualHostId the primary key of the virtual host
	* @return the virtual host
	* @throws PortalException if a virtual host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost getVirtualHost(
		long virtualHostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.getVirtualHost(virtualHostId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the virtual hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of virtual hosts
	* @param end the upper bound of the range of virtual hosts (not inclusive)
	* @return the range of virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.VirtualHost> getVirtualHosts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.getVirtualHosts(start, end);
	}

	/**
	* Returns the number of virtual hosts.
	*
	* @return the number of virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public int getVirtualHostsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.getVirtualHostsCount();
	}

	/**
	* Updates the virtual host in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param virtualHost the virtual host
	* @return the virtual host that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost updateVirtualHost(
		com.liferay.portal.model.VirtualHost virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.updateVirtualHost(virtualHost);
	}

	/**
	* Updates the virtual host in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param virtualHost the virtual host
	* @param merge whether to merge the virtual host with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the virtual host that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost updateVirtualHost(
		com.liferay.portal.model.VirtualHost virtualHost, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.updateVirtualHost(virtualHost, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _virtualHostLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_virtualHostLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.VirtualHost fetchVirtualHost(
		long companyId, long layoutSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.fetchVirtualHost(companyId, layoutSetId);
	}

	public com.liferay.portal.model.VirtualHost fetchVirtualHost(
		java.lang.String hostname)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.fetchVirtualHost(hostname);
	}

	public com.liferay.portal.model.VirtualHost getVirtualHost(long companyId,
		long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.getVirtualHost(companyId, layoutSetId);
	}

	public com.liferay.portal.model.VirtualHost getVirtualHost(
		java.lang.String hostname)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.getVirtualHost(hostname);
	}

	public com.liferay.portal.model.VirtualHost updateVirtualHost(
		long companyId, long layoutSetId, java.lang.String hostname)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _virtualHostLocalService.updateVirtualHost(companyId,
			layoutSetId, hostname);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public VirtualHostLocalService getWrappedVirtualHostLocalService() {
		return _virtualHostLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedVirtualHostLocalService(
		VirtualHostLocalService virtualHostLocalService) {
		_virtualHostLocalService = virtualHostLocalService;
	}

	public VirtualHostLocalService getWrappedService() {
		return _virtualHostLocalService;
	}

	public void setWrappedService(
		VirtualHostLocalService virtualHostLocalService) {
		_virtualHostLocalService = virtualHostLocalService;
	}

	private VirtualHostLocalService _virtualHostLocalService;
}