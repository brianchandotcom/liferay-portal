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
 * Provides a wrapper for {@link ObjectViewService}.
 *
 * @author Marco Leo
 * @see ObjectViewService
 * @generated
 */
public class ObjectViewServiceWrapper
	implements ObjectViewService, ServiceWrapper<ObjectViewService> {

	public ObjectViewServiceWrapper() {
		this(null);
	}

	public ObjectViewServiceWrapper(ObjectViewService objectViewService) {
		_objectViewService = objectViewService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectViewService.getOSGiServiceIdentifier();
	}

	@Override
	public ObjectViewService getWrappedService() {
		return _objectViewService;
	}

	@Override
	public void setWrappedService(ObjectViewService objectViewService) {
		_objectViewService = objectViewService;
	}

	private ObjectViewService _objectViewService;

}