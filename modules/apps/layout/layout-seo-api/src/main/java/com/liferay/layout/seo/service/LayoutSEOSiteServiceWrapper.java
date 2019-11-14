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
 * Provides a wrapper for {@link LayoutSEOSiteService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOSiteService
 * @generated
 */
public class LayoutSEOSiteServiceWrapper
	implements LayoutSEOSiteService, ServiceWrapper<LayoutSEOSiteService> {

	public LayoutSEOSiteServiceWrapper(
		LayoutSEOSiteService layoutSEOSiteService) {

		_layoutSEOSiteService = layoutSEOSiteService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutSEOSiteService.getOSGiServiceIdentifier();
	}

	@Override
	public LayoutSEOSiteService getWrappedService() {
		return _layoutSEOSiteService;
	}

	@Override
	public void setWrappedService(LayoutSEOSiteService layoutSEOSiteService) {
		_layoutSEOSiteService = layoutSEOSiteService;
	}

	private LayoutSEOSiteService _layoutSEOSiteService;

}