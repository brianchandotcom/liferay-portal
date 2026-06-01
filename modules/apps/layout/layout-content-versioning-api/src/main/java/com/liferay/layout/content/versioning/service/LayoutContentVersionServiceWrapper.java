/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.versioning.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutContentVersionService}.
 *
 * @author Lourdes Fernández Besada
 * @see LayoutContentVersionService
 * @generated
 */
public class LayoutContentVersionServiceWrapper
	implements LayoutContentVersionService,
			   ServiceWrapper<LayoutContentVersionService> {

	public LayoutContentVersionServiceWrapper() {
		this(null);
	}

	public LayoutContentVersionServiceWrapper(
		LayoutContentVersionService layoutContentVersionService) {

		_layoutContentVersionService = layoutContentVersionService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutContentVersionService.getOSGiServiceIdentifier();
	}

	@Override
	public LayoutContentVersionService getWrappedService() {
		return _layoutContentVersionService;
	}

	@Override
	public void setWrappedService(
		LayoutContentVersionService layoutContentVersionService) {

		_layoutContentVersionService = layoutContentVersionService;
	}

	private LayoutContentVersionService _layoutContentVersionService;

}
// LIFERAY-SERVICE-BUILDER-HASH:1840797700