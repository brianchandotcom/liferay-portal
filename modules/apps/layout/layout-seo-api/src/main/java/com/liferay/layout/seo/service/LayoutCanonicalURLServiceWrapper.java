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
 * Provides a wrapper for {@link LayoutCanonicalURLService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutCanonicalURLService
 * @generated
 */
@ProviderType
public class LayoutCanonicalURLServiceWrapper
	implements LayoutCanonicalURLService,
			   ServiceWrapper<LayoutCanonicalURLService> {

	public LayoutCanonicalURLServiceWrapper(
		LayoutCanonicalURLService layoutCanonicalURLService) {

		_layoutCanonicalURLService = layoutCanonicalURLService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutCanonicalURLService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
			updateLayoutCanonicalURL(
				long groupId, boolean privateLayout, long layoutId,
				boolean enabled,
				java.util.Map<java.util.Locale, String> canonicalURLMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutCanonicalURLService.updateLayoutCanonicalURL(
			groupId, privateLayout, layoutId, enabled, canonicalURLMap);
	}

	@Override
	public LayoutCanonicalURLService getWrappedService() {
		return _layoutCanonicalURLService;
	}

	@Override
	public void setWrappedService(
		LayoutCanonicalURLService layoutCanonicalURLService) {

		_layoutCanonicalURLService = layoutCanonicalURLService;
	}

	private LayoutCanonicalURLService _layoutCanonicalURLService;

}