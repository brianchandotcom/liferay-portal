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

package com.liferay.marketplace.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AppService}.
 *
 * @author Ryan Park
 * @see AppService
 * @generated
 */
@ProviderType
public class AppServiceWrapper implements AppService,
	ServiceWrapper<AppService> {
	public AppServiceWrapper(AppService appService) {
		_appService = appService;
	}

	@Override
	public com.liferay.marketplace.model.App deleteApp(long appId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _appService.deleteApp(appId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _appService.getOSGiServiceIdentifier();
	}

	@Override
	public void installApp(long remoteAppId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_appService.installApp(remoteAppId);
	}

	@Override
	public void uninstallApp(long remoteAppId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_appService.uninstallApp(remoteAppId);
	}

	@Override
	public com.liferay.marketplace.model.App updateApp(java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _appService.updateApp(file);
	}

	@Override
	public AppService getWrappedService() {
		return _appService;
	}

	@Override
	public void setWrappedService(AppService appService) {
		_appService = appService;
	}

	private AppService _appService;
}