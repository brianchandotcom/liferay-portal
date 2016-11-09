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

package com.liferay.portal.instances.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PortalInstancesLocalService}.
 *
 * @author Michael C. Han
 * @see PortalInstancesLocalService
 * @generated
 */
@ProviderType
public class PortalInstancesLocalServiceWrapper
	implements PortalInstancesLocalService,
		ServiceWrapper<PortalInstancesLocalService> {
	public PortalInstancesLocalServiceWrapper(
		PortalInstancesLocalService portalInstancesLocalService) {
		_portalInstancesLocalService = portalInstancesLocalService;
	}

	@Override
	public boolean isAutoLoginIgnoreHost(java.lang.String host) {
		return _portalInstancesLocalService.isAutoLoginIgnoreHost(host);
	}

	@Override
	public boolean isAutoLoginIgnorePath(java.lang.String path) {
		return _portalInstancesLocalService.isAutoLoginIgnorePath(path);
	}

	@Override
	public boolean isCompanyActive(long companyId) {
		return _portalInstancesLocalService.isCompanyActive(companyId);
	}

	@Override
	public boolean isVirtualHostsIgnoreHost(java.lang.String host) {
		return _portalInstancesLocalService.isVirtualHostsIgnoreHost(host);
	}

	@Override
	public boolean isVirtualHostsIgnorePath(java.lang.String path) {
		return _portalInstancesLocalService.isVirtualHostsIgnorePath(path);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _portalInstancesLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.lang.String[] getWebIds() {
		return _portalInstancesLocalService.getWebIds();
	}

	@Override
	public long getCompanyId(javax.servlet.http.HttpServletRequest request) {
		return _portalInstancesLocalService.getCompanyId(request);
	}

	@Override
	public long getDefaultCompanyId() {
		return _portalInstancesLocalService.getDefaultCompanyId();
	}

	@Override
	public long[] getCompanyIds() {
		return _portalInstancesLocalService.getCompanyIds();
	}

	@Override
	public long[] getCompanyIdsBySQL() throws java.sql.SQLException {
		return _portalInstancesLocalService.getCompanyIdsBySQL();
	}

	@Override
	public void addCompanyId(long companyId) {
		_portalInstancesLocalService.addCompanyId(companyId);
	}

	@Override
	public void initializePortalInstance(
		javax.servlet.ServletContext servletContext, java.lang.String webId) {
		_portalInstancesLocalService.initializePortalInstance(servletContext,
			webId);
	}

	@Override
	public void reload(javax.servlet.ServletContext servletContext) {
		_portalInstancesLocalService.reload(servletContext);
	}

	@Override
	public void removeCompany(long companyId) {
		_portalInstancesLocalService.removeCompany(companyId);
	}

	@Override
	public void synchronizePortalInstances() {
		_portalInstancesLocalService.synchronizePortalInstances();
	}

	@Override
	public PortalInstancesLocalService getWrappedService() {
		return _portalInstancesLocalService;
	}

	@Override
	public void setWrappedService(
		PortalInstancesLocalService portalInstancesLocalService) {
		_portalInstancesLocalService = portalInstancesLocalService;
	}

	private PortalInstancesLocalService _portalInstancesLocalService;
}