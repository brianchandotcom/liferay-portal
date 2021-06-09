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