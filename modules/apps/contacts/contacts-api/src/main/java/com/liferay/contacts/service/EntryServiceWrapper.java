/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link EntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see EntryService
 * @generated
 */
public class EntryServiceWrapper
	implements EntryService, ServiceWrapper<EntryService> {

	public EntryServiceWrapper() {
		this(null);
	}

	public EntryServiceWrapper(EntryService entryService) {
		_entryService = entryService;
	}

	@Override
	public com.liferay.contacts.model.Entry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _entryService.getEntry(entryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _entryService.getOSGiServiceIdentifier();
	}

	@Override
	public EntryService getWrappedService() {
		return _entryService;
	}

	@Override
	public void setWrappedService(EntryService entryService) {
		_entryService = entryService;
	}

	private EntryService _entryService;

}