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

package com.liferay.portlet.softwarecatalog.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SCLicenseService}.
 *
 * @author Brian Wing Shun Chan
 * @see SCLicenseService
 * @generated
 */
@ProviderType
public class SCLicenseServiceWrapper implements SCLicenseService,
	ServiceWrapper<SCLicenseService> {
	public SCLicenseServiceWrapper(SCLicenseService scLicenseService) {
		_scLicenseService = scLicenseService;
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense addLicense(
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scLicenseService.addLicense(name, url, openSource, active,
			recommended);
	}

	@Override
	public void deleteLicense(long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scLicenseService.deleteLicense(licenseId);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense getLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scLicenseService.getLicense(licenseId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _scLicenseService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense updateLicense(
		long licenseId, java.lang.String name, java.lang.String url,
		boolean openSource, boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scLicenseService.updateLicense(licenseId, name, url,
			openSource, active, recommended);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SCLicenseService getWrappedSCLicenseService() {
		return _scLicenseService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSCLicenseService(SCLicenseService scLicenseService) {
		_scLicenseService = scLicenseService;
	}

	@Override
	public SCLicenseService getWrappedService() {
		return _scLicenseService;
	}

	@Override
	public void setWrappedService(SCLicenseService scLicenseService) {
		_scLicenseService = scLicenseService;
	}

	private SCLicenseService _scLicenseService;
}