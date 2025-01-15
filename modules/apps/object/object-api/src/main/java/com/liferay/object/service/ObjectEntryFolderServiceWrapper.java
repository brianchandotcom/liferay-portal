/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectEntryFolderService}.
 *
 * @author Marco Leo
 * @see ObjectEntryFolderService
 * @generated
 */
public class ObjectEntryFolderServiceWrapper
	implements ObjectEntryFolderService,
			   ServiceWrapper<ObjectEntryFolderService> {

	public ObjectEntryFolderServiceWrapper() {
		this(null);
	}

	public ObjectEntryFolderServiceWrapper(
		ObjectEntryFolderService objectEntryFolderService) {

		_objectEntryFolderService = objectEntryFolderService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectEntryFolderService.getOSGiServiceIdentifier();
	}

	@Override
	public ObjectEntryFolderService getWrappedService() {
		return _objectEntryFolderService;
	}

	@Override
	public void setWrappedService(
		ObjectEntryFolderService objectEntryFolderService) {

		_objectEntryFolderService = objectEntryFolderService;
	}

	private ObjectEntryFolderService _objectEntryFolderService;

}