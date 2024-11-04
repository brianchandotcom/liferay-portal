/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPConfigurationEntryService}.
 *
 * @author Marco Leo
 * @see CPConfigurationEntryService
 * @generated
 */
public class CPConfigurationEntryServiceWrapper
	implements CPConfigurationEntryService,
			   ServiceWrapper<CPConfigurationEntryService> {

	public CPConfigurationEntryServiceWrapper() {
		this(null);
	}

	public CPConfigurationEntryServiceWrapper(
		CPConfigurationEntryService cpConfigurationEntryService) {

		_cpConfigurationEntryService = cpConfigurationEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpConfigurationEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public CPConfigurationEntryService getWrappedService() {
		return _cpConfigurationEntryService;
	}

	@Override
	public void setWrappedService(
		CPConfigurationEntryService cpConfigurationEntryService) {

		_cpConfigurationEntryService = cpConfigurationEntryService;
	}

	private CPConfigurationEntryService _cpConfigurationEntryService;

}