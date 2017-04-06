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
 * Provides a wrapper for {@link CommerceProductDefinitionOptionRelService}.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionRelService
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionRelServiceWrapper
	implements CommerceProductDefinitionOptionRelService,
		ServiceWrapper<CommerceProductDefinitionOptionRelService> {
	public CommerceProductDefinitionOptionRelServiceWrapper(
		CommerceProductDefinitionOptionRelService commerceProductDefinitionOptionRelService) {
		_commerceProductDefinitionOptionRelService = commerceProductDefinitionOptionRelService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductDefinitionOptionRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceProductDefinitionOptionRelService getWrappedService() {
		return _commerceProductDefinitionOptionRelService;
	}

	@Override
	public void setWrappedService(
		CommerceProductDefinitionOptionRelService commerceProductDefinitionOptionRelService) {
		_commerceProductDefinitionOptionRelService = commerceProductDefinitionOptionRelService;
	}

	private CommerceProductDefinitionOptionRelService _commerceProductDefinitionOptionRelService;
}