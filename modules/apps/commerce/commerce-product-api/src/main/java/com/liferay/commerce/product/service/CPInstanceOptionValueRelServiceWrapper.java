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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPInstanceOptionValueRelService}.
 *
 * @author Marco Leo
 * @see CPInstanceOptionValueRelService
 * @generated
 */
public class CPInstanceOptionValueRelServiceWrapper
	implements CPInstanceOptionValueRelService,
			   ServiceWrapper<CPInstanceOptionValueRelService> {

	public CPInstanceOptionValueRelServiceWrapper() {
		this(null);
	}

	public CPInstanceOptionValueRelServiceWrapper(
		CPInstanceOptionValueRelService cpInstanceOptionValueRelService) {

		_cpInstanceOptionValueRelService = cpInstanceOptionValueRelService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpInstanceOptionValueRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CPInstanceOptionValueRelService getWrappedService() {
		return _cpInstanceOptionValueRelService;
	}

	@Override
	public void setWrappedService(
		CPInstanceOptionValueRelService cpInstanceOptionValueRelService) {

		_cpInstanceOptionValueRelService = cpInstanceOptionValueRelService;
	}

	private CPInstanceOptionValueRelService _cpInstanceOptionValueRelService;

}