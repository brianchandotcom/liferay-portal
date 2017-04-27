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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPMediaTypeService}.
 *
 * @author Marco Leo
 * @see CPMediaTypeService
 * @generated
 */
@ProviderType
public class CPMediaTypeServiceWrapper implements CPMediaTypeService,
	ServiceWrapper<CPMediaTypeService> {
	public CPMediaTypeServiceWrapper(CPMediaTypeService cpMediaTypeService) {
		_cpMediaTypeService = cpMediaTypeService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpMediaTypeService.getOSGiServiceIdentifier();
	}

	@Override
	public CPMediaTypeService getWrappedService() {
		return _cpMediaTypeService;
	}

	@Override
	public void setWrappedService(CPMediaTypeService cpMediaTypeService) {
		_cpMediaTypeService = cpMediaTypeService;
	}

	private CPMediaTypeService _cpMediaTypeService;
}