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

	public ListTypeDefinitionServiceWrapper() {
		this(null);
	}

	public ListTypeDefinitionServiceWrapper(
		ListTypeDefinitionService listTypeDefinitionService) {

		_listTypeDefinitionService = listTypeDefinitionService;
	}

	@Override
	public com.liferay.list.type.model.ListTypeDefinition addListTypeDefinition(
			java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionService.addListTypeDefinition(nameMap);
	}

	@Override
	public com.liferay.list.type.model.ListTypeDefinition
			deleteListTypeDefinition(
				com.liferay.list.type.model.ListTypeDefinition
					listTypeDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionService.deleteListTypeDefinition(
			listTypeDefinition);
	}

	@Override
	public com.liferay.list.type.model.ListTypeDefinition
			deleteListTypeDefinition(long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionService.deleteListTypeDefinition(
			listTypeDefinitionId);
	}

	@Override
	public com.liferay.list.type.model.ListTypeDefinition getListTypeDefinition(
			long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionService.getListTypeDefinition(
			listTypeDefinitionId);
	}

	@Override
	public java.util.List<com.liferay.list.type.model.ListTypeDefinition>
		getListTypeDefinitions(int start, int end) {

		return _listTypeDefinitionService.getListTypeDefinitions(start, end);
	}

	@Override
	public int getListTypeDefinitionsCount() {
		return _listTypeDefinitionService.getListTypeDefinitionsCount();
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
	public com.liferay.list.type.model.ListTypeDefinition
			updateListTypeDefinition(
				String externalReferenceCode, long listTypeDefinitionId,
				java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionService.updateListTypeDefinition(
			externalReferenceCode, listTypeDefinitionId, nameMap);
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