/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectEntryVersionService}.
 *
 * @author Marco Leo
 * @see ObjectEntryVersionService
 * @generated
 */
public class ObjectEntryVersionServiceWrapper
	implements ObjectEntryVersionService,
			   ServiceWrapper<ObjectEntryVersionService> {

	public ObjectEntryVersionServiceWrapper() {
		this(null);
	}

	public ObjectEntryVersionServiceWrapper(
		ObjectEntryVersionService objectEntryVersionService) {

		_objectEntryVersionService = objectEntryVersionService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectEntryVersionService.getOSGiServiceIdentifier();
	}

	@Override
	public ObjectEntryVersionService getWrappedService() {
		return _objectEntryVersionService;
	}

	@Override
	public void setWrappedService(
		ObjectEntryVersionService objectEntryVersionService) {

		_objectEntryVersionService = objectEntryVersionService;
	}

	private ObjectEntryVersionService _objectEntryVersionService;

}