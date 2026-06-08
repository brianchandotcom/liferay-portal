/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AudienceEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AudienceEntryService
 * @generated
 */
public class AudienceEntryServiceWrapper
	implements AudienceEntryService, ServiceWrapper<AudienceEntryService> {

	public AudienceEntryServiceWrapper() {
		this(null);
	}

	public AudienceEntryServiceWrapper(
		AudienceEntryService audienceEntryService) {

		_audienceEntryService = audienceEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _audienceEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public AudienceEntryService getWrappedService() {
		return _audienceEntryService;
	}

	@Override
	public void setWrappedService(AudienceEntryService audienceEntryService) {
		_audienceEntryService = audienceEntryService;
	}

	private AudienceEntryService _audienceEntryService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-889668691