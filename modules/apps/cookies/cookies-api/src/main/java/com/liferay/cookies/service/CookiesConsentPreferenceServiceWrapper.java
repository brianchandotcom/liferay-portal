/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CookiesConsentPreferenceService}.
 *
 * @author Christopher Kian
 * @see CookiesConsentPreferenceService
 * @generated
 */
public class CookiesConsentPreferenceServiceWrapper
	implements CookiesConsentPreferenceService,
			   ServiceWrapper<CookiesConsentPreferenceService> {

	public CookiesConsentPreferenceServiceWrapper() {
		this(null);
	}

	public CookiesConsentPreferenceServiceWrapper(
		CookiesConsentPreferenceService cookiesConsentPreferenceService) {

		_cookiesConsentPreferenceService = cookiesConsentPreferenceService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cookiesConsentPreferenceService.getOSGiServiceIdentifier();
	}

	@Override
	public CookiesConsentPreferenceService getWrappedService() {
		return _cookiesConsentPreferenceService;
	}

	@Override
	public void setWrappedService(
		CookiesConsentPreferenceService cookiesConsentPreferenceService) {

		_cookiesConsentPreferenceService = cookiesConsentPreferenceService;
	}

	private CookiesConsentPreferenceService _cookiesConsentPreferenceService;

}
// LIFERAY-SERVICE-BUILDER-HASH:178854529