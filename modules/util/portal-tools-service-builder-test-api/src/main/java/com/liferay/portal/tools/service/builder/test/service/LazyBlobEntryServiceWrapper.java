/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LazyBlobEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntryService
 * @generated
 */
public class LazyBlobEntryServiceWrapper
	implements LazyBlobEntryService, ServiceWrapper<LazyBlobEntryService> {

	public LazyBlobEntryServiceWrapper() {
		this(null);
	}

	public LazyBlobEntryServiceWrapper(
		LazyBlobEntryService lazyBlobEntryService) {

		_lazyBlobEntryService = lazyBlobEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _lazyBlobEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public LazyBlobEntryService getWrappedService() {
		return _lazyBlobEntryService;
	}

	@Override
	public void setWrappedService(LazyBlobEntryService lazyBlobEntryService) {
		_lazyBlobEntryService = lazyBlobEntryService;
	}

	private LazyBlobEntryService _lazyBlobEntryService;

}