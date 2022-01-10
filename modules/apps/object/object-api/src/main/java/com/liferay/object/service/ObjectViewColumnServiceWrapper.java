/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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