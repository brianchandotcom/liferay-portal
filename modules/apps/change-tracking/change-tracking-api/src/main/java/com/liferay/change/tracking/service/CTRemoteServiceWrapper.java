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

package com.liferay.change.tracking.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CTRemoteService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTRemoteService
 * @generated
 */
public class CTRemoteServiceWrapper
	implements CTRemoteService, ServiceWrapper<CTRemoteService> {

	public CTRemoteServiceWrapper() {
		this(null);
	}

	public CTRemoteServiceWrapper(CTRemoteService ctRemoteService) {
		_ctRemoteService = ctRemoteService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctRemoteService.getOSGiServiceIdentifier();
	}

	@Override
	public CTRemoteService getWrappedService() {
		return _ctRemoteService;
	}

	@Override
	public void setWrappedService(CTRemoteService ctRemoteService) {
		_ctRemoteService = ctRemoteService;
	}

	private CTRemoteService _ctRemoteService;

}