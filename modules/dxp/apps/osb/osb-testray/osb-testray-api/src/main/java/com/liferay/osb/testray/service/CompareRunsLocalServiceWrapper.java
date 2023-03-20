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

package com.liferay.osb.testray.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CompareRunsLocalService}.
 *
 * @author José Abelenda
 * @see CompareRunsLocalService
 * @generated
 */
public class CompareRunsLocalServiceWrapper
	implements CompareRunsLocalService,
			   ServiceWrapper<CompareRunsLocalService> {

	public CompareRunsLocalServiceWrapper() {
		this(null);
	}

	public CompareRunsLocalServiceWrapper(
		CompareRunsLocalService compareRunsLocalService) {

		_compareRunsLocalService = compareRunsLocalService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _compareRunsLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public CompareRunsLocalService getWrappedService() {
		return _compareRunsLocalService;
	}

	@Override
	public void setWrappedService(
		CompareRunsLocalService compareRunsLocalService) {

		_compareRunsLocalService = compareRunsLocalService;
	}

	private CompareRunsLocalService _compareRunsLocalService;

}