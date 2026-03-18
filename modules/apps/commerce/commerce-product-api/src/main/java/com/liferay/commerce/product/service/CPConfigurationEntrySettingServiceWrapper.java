/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPConfigurationEntrySettingService}.
 *
 * @author Marco Leo
 * @see CPConfigurationEntrySettingService
 * @generated
 */
public class CPConfigurationEntrySettingServiceWrapper
	implements CPConfigurationEntrySettingService,
			   ServiceWrapper<CPConfigurationEntrySettingService> {

	public CPConfigurationEntrySettingServiceWrapper() {
		this(null);
	}

	public CPConfigurationEntrySettingServiceWrapper(
		CPConfigurationEntrySettingService cpConfigurationEntrySettingService) {

		_cpConfigurationEntrySettingService =
			cpConfigurationEntrySettingService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpConfigurationEntrySettingService.getOSGiServiceIdentifier();
	}

	@Override
	public CPConfigurationEntrySettingService getWrappedService() {
		return _cpConfigurationEntrySettingService;
	}

	@Override
	public void setWrappedService(
		CPConfigurationEntrySettingService cpConfigurationEntrySettingService) {

		_cpConfigurationEntrySettingService =
			cpConfigurationEntrySettingService;
	}

	private CPConfigurationEntrySettingService
		_cpConfigurationEntrySettingService;

}
// LIFERAY-SERVICE-BUILDER-HASH:181837507