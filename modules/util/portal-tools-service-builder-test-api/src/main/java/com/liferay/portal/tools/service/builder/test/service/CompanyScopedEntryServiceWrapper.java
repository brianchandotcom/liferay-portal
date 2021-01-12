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
 * Provides a wrapper for {@link CompanyScopedEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see CompanyScopedEntryService
 * @generated
 */
public class CompanyScopedEntryServiceWrapper
	implements CompanyScopedEntryService,
			   ServiceWrapper<CompanyScopedEntryService> {

	public CompanyScopedEntryServiceWrapper(
		CompanyScopedEntryService companyScopedEntryService) {

		_companyScopedEntryService = companyScopedEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _companyScopedEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public CompanyScopedEntryService getWrappedService() {
		return _companyScopedEntryService;
	}

	@Override
	public void setWrappedService(
		CompanyScopedEntryService companyScopedEntryService) {

		_companyScopedEntryService = companyScopedEntryService;
	}

	private CompanyScopedEntryService _companyScopedEntryService;

}