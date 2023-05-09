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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PortalInstancesLocalService}.
 *
 * @author Michael C. Han
 * @see PortalInstancesLocalService
 * @generated
 */
public class PortalInstancesLocalServiceWrapper
	implements PortalInstancesLocalService,
			   ServiceWrapper<PortalInstancesLocalService> {

	public PortalInstancesLocalServiceWrapper() {
		this(null);
	}

	public PortalInstancesLocalServiceWrapper(
		PortalInstancesLocalService portalInstancesLocalService) {

		_portalInstancesLocalService = portalInstancesLocalService;
	}

	@Override
	public long[] getCompanyIds() {
		return _portalInstancesLocalService.getCompanyIds();
	}

	@Override
	public long getDefaultCompanyId() {
		return _portalInstancesLocalService.getDefaultCompanyId();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _portalInstancesLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public void initializePortalInstance(
			long companyId, String siteInitializerKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_portalInstancesLocalService.initializePortalInstance(
			companyId, siteInitializerKey);
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