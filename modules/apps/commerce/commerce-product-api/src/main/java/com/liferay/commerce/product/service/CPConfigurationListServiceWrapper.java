/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPConfigurationListService}.
 *
 * @author Marco Leo
 * @see CPConfigurationListService
 * @generated
 */
public class CPConfigurationListServiceWrapper
	implements CPConfigurationListService,
			   ServiceWrapper<CPConfigurationListService> {

	public CPConfigurationListServiceWrapper() {
		this(null);
	}

	public CPConfigurationListServiceWrapper(
		CPConfigurationListService cpConfigurationListService) {

		_cpConfigurationListService = cpConfigurationListService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpConfigurationListService.getOSGiServiceIdentifier();
	}

	@Override
	public CPConfigurationListService getWrappedService() {
		return _cpConfigurationListService;
	}

	@Override
	public void setWrappedService(
		CPConfigurationListService cpConfigurationListService) {

		_cpConfigurationListService = cpConfigurationListService;
	}

	private CPConfigurationListService _cpConfigurationListService;

}