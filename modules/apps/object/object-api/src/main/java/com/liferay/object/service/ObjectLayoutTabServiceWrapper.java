/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectLayoutTabService}.
 *
 * @author Marco Leo
 * @see ObjectLayoutTabService
 * @generated
 */
public class ObjectLayoutTabServiceWrapper
	implements ObjectLayoutTabService, ServiceWrapper<ObjectLayoutTabService> {

	public ObjectLayoutTabServiceWrapper() {
		this(null);
	}

	public ObjectLayoutTabServiceWrapper(
		ObjectLayoutTabService objectLayoutTabService) {

		_objectLayoutTabService = objectLayoutTabService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectLayoutTabService.getOSGiServiceIdentifier();
	}

	@Override
	public ObjectLayoutTabService getWrappedService() {
		return _objectLayoutTabService;
	}

	@Override
	public void setWrappedService(
		ObjectLayoutTabService objectLayoutTabService) {

		_objectLayoutTabService = objectLayoutTabService;
	}

	private ObjectLayoutTabService _objectLayoutTabService;

}