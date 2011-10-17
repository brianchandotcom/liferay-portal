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

package com.liferay.portlet.softwarecatalog.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SCLicenseService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCLicenseService
 * @generated
 */
public class SCLicenseServiceWrapper implements SCLicenseService,
	ServiceWrapper<SCLicenseService> {
	public SCLicenseServiceWrapper(SCLicenseService scLicenseService) {
		_scLicenseService = scLicenseService;
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense addLicense(
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseService.addLicense(name, url, openSource, active,
			recommended);
	}

	public void deleteLicense(long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scLicenseService.deleteLicense(licenseId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense getLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseService.getLicense(licenseId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense updateLicense(
		long licenseId, java.lang.String name, java.lang.String url,
		boolean openSource, boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseService.updateLicense(licenseId, name, url,
			openSource, active, recommended);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SCLicenseService getWrappedSCLicenseService() {
		return _scLicenseService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSCLicenseService(SCLicenseService scLicenseService) {
		_scLicenseService = scLicenseService;
	}

	public SCLicenseService getWrappedService() {
		return _scLicenseService;
	}

	public void setWrappedService(SCLicenseService scLicenseService) {
		_scLicenseService = scLicenseService;
	}

	private SCLicenseService _scLicenseService;
}