/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AppService}.
 *
 * @author Ryan Park
 * @see AppService
 * @generated
 */
public class AppServiceWrapper
	implements AppService, ServiceWrapper<AppService> {

	public AppServiceWrapper() {
		this(null);
	}

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
	public String getOSGiServiceIdentifier() {
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