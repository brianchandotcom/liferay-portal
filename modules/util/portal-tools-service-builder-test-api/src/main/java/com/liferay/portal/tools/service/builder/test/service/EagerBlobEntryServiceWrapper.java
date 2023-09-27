/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link EagerBlobEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntryService
 * @generated
 */
public class EagerBlobEntryServiceWrapper
	implements EagerBlobEntryService, ServiceWrapper<EagerBlobEntryService> {

	public EagerBlobEntryServiceWrapper() {
		this(null);
	}

	public EagerBlobEntryServiceWrapper(
		EagerBlobEntryService eagerBlobEntryService) {

		_eagerBlobEntryService = eagerBlobEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _eagerBlobEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public EagerBlobEntryService getWrappedService() {
		return _eagerBlobEntryService;
	}

	@Override
	public void setWrappedService(EagerBlobEntryService eagerBlobEntryService) {
		_eagerBlobEntryService = eagerBlobEntryService;
	}

	private EagerBlobEntryService _eagerBlobEntryService;

}