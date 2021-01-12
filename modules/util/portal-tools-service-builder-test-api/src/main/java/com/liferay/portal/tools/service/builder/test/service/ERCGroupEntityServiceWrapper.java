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

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ERCGroupEntityService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntityService
 * @generated
 */
public class ERCGroupEntityServiceWrapper
	implements ERCGroupEntityService, ServiceWrapper<ERCGroupEntityService> {

	public ERCGroupEntityServiceWrapper(
		ERCGroupEntityService ercGroupEntityService) {

		_ercGroupEntityService = ercGroupEntityService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ercGroupEntityService.getOSGiServiceIdentifier();
	}

	@Override
	public ERCGroupEntityService getWrappedService() {
		return _ercGroupEntityService;
	}

	@Override
	public void setWrappedService(ERCGroupEntityService ercGroupEntityService) {
		_ercGroupEntityService = ercGroupEntityService;
	}

	private ERCGroupEntityService _ercGroupEntityService;

}