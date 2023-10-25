/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SavedContentEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see SavedContentEntryService
 * @generated
 */
public class SavedContentEntryServiceWrapper
	implements SavedContentEntryService,
			   ServiceWrapper<SavedContentEntryService> {

	public SavedContentEntryServiceWrapper() {
		this(null);
	}

	public SavedContentEntryServiceWrapper(
		SavedContentEntryService savedContentEntryService) {

		_savedContentEntryService = savedContentEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _savedContentEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public SavedContentEntryService getWrappedService() {
		return _savedContentEntryService;
	}

	@Override
	public void setWrappedService(
		SavedContentEntryService savedContentEntryService) {

		_savedContentEntryService = savedContentEntryService;
	}

	private SavedContentEntryService _savedContentEntryService;

}