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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMStructureLayoutService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayoutService
 * @generated
 */
@ProviderType
public class DDMStructureLayoutServiceWrapper
	implements DDMStructureLayoutService,
			   ServiceWrapper<DDMStructureLayoutService> {

	public DDMStructureLayoutServiceWrapper(
		DDMStructureLayoutService ddmStructureLayoutService) {

		_ddmStructureLayoutService = ddmStructureLayoutService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddmStructureLayoutService.getOSGiServiceIdentifier();
	}

	@Override
	public DDMStructureLayoutService getWrappedService() {
		return _ddmStructureLayoutService;
	}

	@Override
	public void setWrappedService(
		DDMStructureLayoutService ddmStructureLayoutService) {

		_ddmStructureLayoutService = ddmStructureLayoutService;
	}

	private DDMStructureLayoutService _ddmStructureLayoutService;

}