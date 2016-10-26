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
 * Provides a wrapper for {@link PortalInstanceLocalService}.
 *
 * @author Michael C. Han
 * @see PortalInstanceLocalService
 * @generated
 */
@ProviderType
public class PortalInstanceLocalServiceWrapper
	implements PortalInstanceLocalService,
		ServiceWrapper<PortalInstanceLocalService> {
	public PortalInstanceLocalServiceWrapper(
		PortalInstanceLocalService portalInstanceLocalService) {
		_portalInstanceLocalService = portalInstanceLocalService;
	}

	@Override
	public boolean isAutoLoginIgnoreHost(java.lang.String host) {
		return _portalInstanceLocalService.isAutoLoginIgnoreHost(host);
	}

	@Override
	public boolean isAutoLoginIgnorePath(java.lang.String path) {
		return _portalInstanceLocalService.isAutoLoginIgnorePath(path);
	}

	@Override
	public boolean isCompanyActive(long companyId) {
		return _portalInstanceLocalService.isCompanyActive(companyId);
	}

	@Override
	public boolean isVirtualHostsIgnoreHost(java.lang.String host) {
		return _portalInstanceLocalService.isVirtualHostsIgnoreHost(host);
	}

	@Override
	public boolean isVirtualHostsIgnorePath(java.lang.String path) {
		return _portalInstanceLocalService.isVirtualHostsIgnorePath(path);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _portalInstanceLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.lang.String[] getWebIds() {
		return _portalInstanceLocalService.getWebIds();
	}

	@Override
	public long getCompanyId(javax.servlet.http.HttpServletRequest request) {
		return _portalInstanceLocalService.getCompanyId(request);
	}

	@Override
	public long getDefaultCompanyId() {
		return _portalInstanceLocalService.getDefaultCompanyId();
	}

	@Override
	public long[] getCompanyIds() {
		return _portalInstanceLocalService.getCompanyIds();
	}

	@Override
	public long[] getCompanyIdsBySQL() throws java.sql.SQLException {
		return _portalInstanceLocalService.getCompanyIdsBySQL();
	}

	@Override
	public void addCompanyId(long companyId) {
		_portalInstanceLocalService.addCompanyId(companyId);
	}

	@Override
	public void initializePortalInstance(
		javax.servlet.ServletContext servletContext, java.lang.String webId) {
		_portalInstanceLocalService.initializePortalInstance(servletContext,
			webId);
	}

	@Override
	public void reload(javax.servlet.ServletContext servletContext) {
		_portalInstanceLocalService.reload(servletContext);
	}

	@Override
	public void removeCompany(long companyId) {
		_portalInstanceLocalService.removeCompany(companyId);
	}

	@Override
	public void synchronizePortalInstances() {
		_portalInstanceLocalService.synchronizePortalInstances();
	}

	@Override
	public PortalInstanceLocalService getWrappedService() {
		return _portalInstanceLocalService;
	}

	@Override
	public void setWrappedService(
		PortalInstanceLocalService portalInstanceLocalService) {
		_portalInstanceLocalService = portalInstanceLocalService;
	}

	private PortalInstanceLocalService _portalInstanceLocalService;
}