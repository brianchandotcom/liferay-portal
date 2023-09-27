/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectViewColumnService}.
 *
 * @author Marco Leo
 * @see ObjectViewColumnService
 * @generated
 */
public class ObjectViewColumnServiceWrapper
	implements ObjectViewColumnService,
			   ServiceWrapper<ObjectViewColumnService> {

	public ObjectViewColumnServiceWrapper() {
		this(null);
	}

	public ObjectViewColumnServiceWrapper(
		ObjectViewColumnService objectViewColumnService) {

		_objectViewColumnService = objectViewColumnService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectViewColumnService.getOSGiServiceIdentifier();
	}

	@Override
	public ObjectViewColumnService getWrappedService() {
		return _objectViewColumnService;
	}

	@Override
	public void setWrappedService(
		ObjectViewColumnService objectViewColumnService) {

		_objectViewColumnService = objectViewColumnService;
	}

	private ObjectViewColumnService _objectViewColumnService;

}