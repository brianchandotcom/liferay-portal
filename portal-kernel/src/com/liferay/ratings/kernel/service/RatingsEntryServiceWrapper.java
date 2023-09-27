/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.ratings.kernel.model.RatingsEntry;

/**
 * Provides a wrapper for {@link RatingsEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntryService
 * @generated
 */
public class RatingsEntryServiceWrapper
	implements RatingsEntryService, ServiceWrapper<RatingsEntryService> {

	public RatingsEntryServiceWrapper() {
		this(null);
	}

	public RatingsEntryServiceWrapper(RatingsEntryService ratingsEntryService) {
		_ratingsEntryService = ratingsEntryService;
	}

	@Override
	public void deleteEntry(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ratingsEntryService.deleteEntry(className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ratingsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public RatingsEntry updateEntry(
			String className, long classPK, double score)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ratingsEntryService.updateEntry(className, classPK, score);
	}

	@Override
	public RatingsEntryService getWrappedService() {
		return _ratingsEntryService;
	}

	@Override
	public void setWrappedService(RatingsEntryService ratingsEntryService) {
		_ratingsEntryService = ratingsEntryService;
	}

	private RatingsEntryService _ratingsEntryService;

}