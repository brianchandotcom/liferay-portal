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

package com.liferay.list.type.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ListTypeDefinitionService}.
 *
 * @author Gabriel Albuquerque
 * @see ListTypeDefinitionService
 * @generated
 */
public class ListTypeDefinitionServiceWrapper
	implements ListTypeDefinitionService,
			   ServiceWrapper<ListTypeDefinitionService> {

	public ListTypeDefinitionServiceWrapper(
		ListTypeDefinitionService listTypeDefinitionService) {

		_listTypeDefinitionService = listTypeDefinitionService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _listTypeDefinitionService.getOSGiServiceIdentifier();
	}

	@Override
	public ListTypeDefinitionService getWrappedService() {
		return _listTypeDefinitionService;
	}

	@Override
	public void setWrappedService(
		ListTypeDefinitionService listTypeDefinitionService) {

		_listTypeDefinitionService = listTypeDefinitionService;
	}

	private ListTypeDefinitionService _listTypeDefinitionService;

}