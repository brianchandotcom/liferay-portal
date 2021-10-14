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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ScreensDDMStructureVersionService}.
 *
 * @author José Manuel Navarro
 * @see ScreensDDMStructureVersionService
 * @generated
 */
public class ScreensDDMStructureVersionServiceWrapper
	implements ScreensDDMStructureVersionService,
			   ServiceWrapper<ScreensDDMStructureVersionService> {

	public ScreensDDMStructureVersionServiceWrapper(
		ScreensDDMStructureVersionService screensDDMStructureVersionService) {

		_screensDDMStructureVersionService = screensDDMStructureVersionService;
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getDDMStructureVersion(
			long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _screensDDMStructureVersionService.getDDMStructureVersion(
			structureId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _screensDDMStructureVersionService.getOSGiServiceIdentifier();
	}

	@Override
	public ScreensDDMStructureVersionService getWrappedService() {
		return _screensDDMStructureVersionService;
	}

	@Override
	public void setWrappedService(
		ScreensDDMStructureVersionService screensDDMStructureVersionService) {

		_screensDDMStructureVersionService = screensDDMStructureVersionService;
	}

	private ScreensDDMStructureVersionService
		_screensDDMStructureVersionService;

}