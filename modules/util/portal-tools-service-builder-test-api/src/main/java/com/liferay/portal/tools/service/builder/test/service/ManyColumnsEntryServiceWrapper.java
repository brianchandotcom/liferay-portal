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
 * Provides a wrapper for {@link ManyColumnsEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see ManyColumnsEntryService
 * @generated
 */
public class ManyColumnsEntryServiceWrapper
	implements ManyColumnsEntryService,
			   ServiceWrapper<ManyColumnsEntryService> {

	public ManyColumnsEntryServiceWrapper(
		ManyColumnsEntryService manyColumnsEntryService) {

		_manyColumnsEntryService = manyColumnsEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _manyColumnsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public ManyColumnsEntryService getWrappedService() {
		return _manyColumnsEntryService;
	}

	@Override
	public void setWrappedService(
		ManyColumnsEntryService manyColumnsEntryService) {

		_manyColumnsEntryService = manyColumnsEntryService;
	}

	private ManyColumnsEntryService _manyColumnsEntryService;

}