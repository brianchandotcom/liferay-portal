/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceAddressRestrictionService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionService
 * @generated
 */
public class CommerceAddressRestrictionServiceWrapper
	implements CommerceAddressRestrictionService,
			   ServiceWrapper<CommerceAddressRestrictionService> {

	public CommerceAddressRestrictionServiceWrapper() {
		this(null);
	}

	public CommerceAddressRestrictionServiceWrapper(
		CommerceAddressRestrictionService commerceAddressRestrictionService) {

		_commerceAddressRestrictionService = commerceAddressRestrictionService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAddressRestrictionService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceAddressRestrictionService getWrappedService() {
		return _commerceAddressRestrictionService;
	}

	@Override
	public void setWrappedService(
		CommerceAddressRestrictionService commerceAddressRestrictionService) {

		_commerceAddressRestrictionService = commerceAddressRestrictionService;
	}

	private CommerceAddressRestrictionService
		_commerceAddressRestrictionService;

}