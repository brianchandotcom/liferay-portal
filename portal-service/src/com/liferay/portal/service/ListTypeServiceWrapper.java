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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link ListTypeService}.
 *
 * @author Brian Wing Shun Chan
 * @see ListTypeService
 * @generated
 */
@ProviderType
public class ListTypeServiceWrapper implements ListTypeService,
	ServiceWrapper<ListTypeService> {
	public ListTypeServiceWrapper(ListTypeService listTypeService) {
		_listTypeService = listTypeService;
	}

	@Override
	public com.liferay.portal.model.ListType getListType(long listTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _listTypeService.getListType(listTypeId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.ListType> getListTypes(
		java.lang.String type) {
		return _listTypeService.getListTypes(type);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _listTypeService.getOSGiServiceIdentifier();
	}

	@Override
	public void validate(long listTypeId, long classNameId,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException {
		_listTypeService.validate(listTypeId, classNameId, type);
	}

	@Override
	public void validate(long listTypeId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException {
		_listTypeService.validate(listTypeId, type);
	}

	@Override
	public ListTypeService getWrappedService() {
		return _listTypeService;
	}

	@Override
	public void setWrappedService(ListTypeService listTypeService) {
		_listTypeService = listTypeService;
	}

	private ListTypeService _listTypeService;
}