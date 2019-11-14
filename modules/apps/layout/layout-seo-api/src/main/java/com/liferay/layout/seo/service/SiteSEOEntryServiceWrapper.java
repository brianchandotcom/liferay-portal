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

package com.liferay.layout.seo.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SiteSEOEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see SiteSEOEntryService
 * @generated
 */
public class SiteSEOEntryServiceWrapper
	implements ServiceWrapper<SiteSEOEntryService>, SiteSEOEntryService {

	public SiteSEOEntryServiceWrapper(SiteSEOEntryService siteSEOEntryService) {
		_siteSEOEntryService = siteSEOEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _siteSEOEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public SiteSEOEntryService getWrappedService() {
		return _siteSEOEntryService;
	}

	@Override
	public void setWrappedService(SiteSEOEntryService siteSEOEntryService) {
		_siteSEOEntryService = siteSEOEntryService;
	}

	private SiteSEOEntryService _siteSEOEntryService;

}