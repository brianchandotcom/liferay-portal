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
 * Provides a wrapper for {@link CPTemplateLayoutEntryService}.
 *
 * @author Marco Leo
 * @see CPTemplateLayoutEntryService
 * @generated
 */
@ProviderType
public class CPTemplateLayoutEntryServiceWrapper
	implements CPTemplateLayoutEntryService,
		ServiceWrapper<CPTemplateLayoutEntryService> {
	public CPTemplateLayoutEntryServiceWrapper(
		CPTemplateLayoutEntryService cpTemplateLayoutEntryService) {
		_cpTemplateLayoutEntryService = cpTemplateLayoutEntryService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpTemplateLayoutEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public CPTemplateLayoutEntryService getWrappedService() {
		return _cpTemplateLayoutEntryService;
	}

	@Override
	public void setWrappedService(
		CPTemplateLayoutEntryService cpTemplateLayoutEntryService) {
		_cpTemplateLayoutEntryService = cpTemplateLayoutEntryService;
	}

	private CPTemplateLayoutEntryService _cpTemplateLayoutEntryService;
}