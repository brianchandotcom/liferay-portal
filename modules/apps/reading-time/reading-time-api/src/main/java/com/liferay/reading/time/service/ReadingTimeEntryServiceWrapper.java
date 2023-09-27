/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ReadingTimeEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see ReadingTimeEntryService
 * @generated
 */
public class ReadingTimeEntryServiceWrapper
	implements ReadingTimeEntryService,
			   ServiceWrapper<ReadingTimeEntryService> {

	public ReadingTimeEntryServiceWrapper() {
		this(null);
	}

	public ReadingTimeEntryServiceWrapper(
		ReadingTimeEntryService readingTimeEntryService) {

		_readingTimeEntryService = readingTimeEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _readingTimeEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public ReadingTimeEntryService getWrappedService() {
		return _readingTimeEntryService;
	}

	@Override
	public void setWrappedService(
		ReadingTimeEntryService readingTimeEntryService) {

		_readingTimeEntryService = readingTimeEntryService;
	}

	private ReadingTimeEntryService _readingTimeEntryService;

}