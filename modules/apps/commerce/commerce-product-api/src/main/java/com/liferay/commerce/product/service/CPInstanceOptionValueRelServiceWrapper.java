/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPInstanceOptionValueRelService}.
 *
 * @author Marco Leo
 * @see CPInstanceOptionValueRelService
 * @generated
 */
public class CPInstanceOptionValueRelServiceWrapper
	implements CPInstanceOptionValueRelService,
			   ServiceWrapper<CPInstanceOptionValueRelService> {

	public CPInstanceOptionValueRelServiceWrapper() {
		this(null);
	}

	public CPInstanceOptionValueRelServiceWrapper(
		CPInstanceOptionValueRelService cpInstanceOptionValueRelService) {

		_cpInstanceOptionValueRelService = cpInstanceOptionValueRelService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpInstanceOptionValueRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CPInstanceOptionValueRelService getWrappedService() {
		return _cpInstanceOptionValueRelService;
	}

	@Override
	public void setWrappedService(
		CPInstanceOptionValueRelService cpInstanceOptionValueRelService) {

		_cpInstanceOptionValueRelService = cpInstanceOptionValueRelService;
	}

	private CPInstanceOptionValueRelService _cpInstanceOptionValueRelService;

}