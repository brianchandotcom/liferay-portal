/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public LayoutSEOSiteServiceWrapper() {
		this(null);
	}

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