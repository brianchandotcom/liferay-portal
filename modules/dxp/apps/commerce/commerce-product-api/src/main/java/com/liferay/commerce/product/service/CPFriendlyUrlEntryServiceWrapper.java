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
 * Provides a wrapper for {@link CPFriendlyUrlEntryService}.
 *
 * @author Marco Leo
 * @see CPFriendlyUrlEntryService
 * @generated
 */
@ProviderType
public class CPFriendlyUrlEntryServiceWrapper
	implements CPFriendlyUrlEntryService,
		ServiceWrapper<CPFriendlyUrlEntryService> {
	public CPFriendlyUrlEntryServiceWrapper(
		CPFriendlyUrlEntryService cpFriendlyUrlEntryService) {
		_cpFriendlyUrlEntryService = cpFriendlyUrlEntryService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpFriendlyUrlEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public CPFriendlyUrlEntryService getWrappedService() {
		return _cpFriendlyUrlEntryService;
	}

	@Override
	public void setWrappedService(
		CPFriendlyUrlEntryService cpFriendlyUrlEntryService) {
		_cpFriendlyUrlEntryService = cpFriendlyUrlEntryService;
	}

	private CPFriendlyUrlEntryService _cpFriendlyUrlEntryService;
}