/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ConsentPreferenceService}.
 *
 * @author Brian Wing Shun Chan
 * @see ConsentPreferenceService
 * @generated
 */
public class ConsentPreferenceServiceWrapper
	implements ConsentPreferenceService,
			   ServiceWrapper<ConsentPreferenceService> {

	public ConsentPreferenceServiceWrapper() {
		this(null);
	}

	public ConsentPreferenceServiceWrapper(
		ConsentPreferenceService consentPreferenceService) {

		_consentPreferenceService = consentPreferenceService;
	}

	@Override
	public com.liferay.cookies.model.ConsentPreference addConsentPreference(
			long userId, String domain, java.util.Date expirationDate,
			String name, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _consentPreferenceService.addConsentPreference(
			userId, domain, expirationDate, name, value);
	}

	@Override
	public void deleteConsentPreference(long userId, String domain, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		_consentPreferenceService.deleteConsentPreference(userId, domain, name);
	}

	@Override
	public void deleteConsentPreferences(long userId) {
		_consentPreferenceService.deleteConsentPreferences(userId);
	}

	@Override
	public void deleteConsentPreferences(long userId, String domain) {
		_consentPreferenceService.deleteConsentPreferences(userId, domain);
	}

	@Override
	public com.liferay.cookies.model.ConsentPreference getConsentPreference(
		long userId, String domain, String name) {

		return _consentPreferenceService.getConsentPreference(
			userId, domain, name);
	}

	@Override
	public java.util.List<com.liferay.cookies.model.ConsentPreference>
		getConsentPreferences(long userId, String domain) {

		return _consentPreferenceService.getConsentPreferences(userId, domain);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _consentPreferenceService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.cookies.model.ConsentPreference updateConsentPreference(
		com.liferay.cookies.model.ConsentPreference consentPreference) {

		return _consentPreferenceService.updateConsentPreference(
			consentPreference);
	}

	@Override
	public ConsentPreferenceService getWrappedService() {
		return _consentPreferenceService;
	}

	@Override
	public void setWrappedService(
		ConsentPreferenceService consentPreferenceService) {

		_consentPreferenceService = consentPreferenceService;
	}

	private ConsentPreferenceService _consentPreferenceService;

}
// LIFERAY-SERVICE-BUILDER-HASH:2042930996