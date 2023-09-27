/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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