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

package com.liferay.screens.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ScreensUserService}.
 *
 * @author José Manuel Navarro
 * @see ScreensUserService
 * @generated
 */
@ProviderType
public class ScreensUserServiceWrapper implements ScreensUserService,
	ServiceWrapper<ScreensUserService> {
	public ScreensUserServiceWrapper(ScreensUserService screensUserService) {
		_screensUserService = screensUserService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _screensUserService.getOSGiServiceIdentifier();
	}

	@Override
	public ScreensUserService getWrappedService() {
		return _screensUserService;
	}

	@Override
	public void setWrappedService(ScreensUserService screensUserService) {
		_screensUserService = screensUserService;
	}

	private ScreensUserService _screensUserService;
}