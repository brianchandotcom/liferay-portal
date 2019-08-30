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

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link LayoutSEOCanonicalURLService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOCanonicalURLService
 * @generated
 */
@ProviderType
public class LayoutSEOCanonicalURLServiceWrapper
	implements LayoutSEOCanonicalURLService,
			   ServiceWrapper<LayoutSEOCanonicalURLService> {

	public LayoutSEOCanonicalURLServiceWrapper(
		LayoutSEOCanonicalURLService layoutSEOCanonicalURLService) {

		_layoutSEOCanonicalURLService = layoutSEOCanonicalURLService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutSEOCanonicalURLService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
			updateLayoutSEOCanonicalURL(
				long groupId, boolean privateLayout, long layoutId,
				boolean enabled,
				java.util.Map<java.util.Locale, String> canonicalURLMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOCanonicalURLService.updateLayoutSEOCanonicalURL(
			groupId, privateLayout, layoutId, enabled, canonicalURLMap,
			serviceContext);
	}

	@Override
	public LayoutSEOCanonicalURLService getWrappedService() {
		return _layoutSEOCanonicalURLService;
	}

	@Override
	public void setWrappedService(
		LayoutSEOCanonicalURLService layoutSEOCanonicalURLService) {

		_layoutSEOCanonicalURLService = layoutSEOCanonicalURLService;
	}

	private LayoutSEOCanonicalURLService _layoutSEOCanonicalURLService;

}