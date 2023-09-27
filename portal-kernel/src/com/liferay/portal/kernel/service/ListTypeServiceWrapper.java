/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link ListTypeService}.
 *
 * @author Brian Wing Shun Chan
 * @see ListTypeService
 * @generated
 */
public class ListTypeServiceWrapper
	implements ListTypeService, ServiceWrapper<ListTypeService> {

	public ListTypeServiceWrapper() {
		this(null);
	}

	public ListTypeServiceWrapper(ListTypeService listTypeService) {
		_listTypeService = listTypeService;
	}

	@Override
	public com.liferay.portal.kernel.model.ListType getListType(long listTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeService.getListType(listTypeId);
	}

	@Override
	public com.liferay.portal.kernel.model.ListType getListType(
		java.lang.String name, java.lang.String type) {

		return _listTypeService.getListType(name, type);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.ListType>
		getListTypes(java.lang.String type) {

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
	public void validate(
			long listTypeId, long classNameId, java.lang.String type)
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