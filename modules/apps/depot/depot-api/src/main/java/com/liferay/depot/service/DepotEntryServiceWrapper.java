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

package com.liferay.depot.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DepotEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryService
 * @generated
 */
public class DepotEntryServiceWrapper
	implements DepotEntryService, ServiceWrapper<DepotEntryService> {

	public DepotEntryServiceWrapper(DepotEntryService depotEntryService) {
		_depotEntryService = depotEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _depotEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public DepotEntryService getWrappedService() {
		return _depotEntryService;
	}

	@Override
	public void setWrappedService(DepotEntryService depotEntryService) {
		_depotEntryService = depotEntryService;
	}

	private DepotEntryService _depotEntryService;

}