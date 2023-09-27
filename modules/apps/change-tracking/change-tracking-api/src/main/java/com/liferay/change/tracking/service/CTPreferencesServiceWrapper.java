/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CTPreferencesService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTPreferencesService
 * @generated
 */
public class CTPreferencesServiceWrapper
	implements CTPreferencesService, ServiceWrapper<CTPreferencesService> {

	public CTPreferencesServiceWrapper() {
		this(null);
	}

	public CTPreferencesServiceWrapper(
		CTPreferencesService ctPreferencesService) {

		_ctPreferencesService = ctPreferencesService;
	}

	@Override
	public com.liferay.change.tracking.model.CTPreferences checkoutCTCollection(
			long companyId, long userId, long ctCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctPreferencesService.checkoutCTCollection(
			companyId, userId, ctCollectionId);
	}

	@Override
	public com.liferay.change.tracking.model.CTPreferences enablePublications(
			long companyId, boolean enable)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctPreferencesService.enablePublications(companyId, enable);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctPreferencesService.getOSGiServiceIdentifier();
	}

	@Override
	public CTPreferencesService getWrappedService() {
		return _ctPreferencesService;
	}

	@Override
	public void setWrappedService(CTPreferencesService ctPreferencesService) {
		_ctPreferencesService = ctPreferencesService;
	}

	private CTPreferencesService _ctPreferencesService;

}