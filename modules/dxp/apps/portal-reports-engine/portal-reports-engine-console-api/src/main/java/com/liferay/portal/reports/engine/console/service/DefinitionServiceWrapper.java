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

package com.liferay.portal.reports.engine.console.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DefinitionService}.
 *
 * @author Brian Wing Shun Chan
 * @see DefinitionService
 * @generated
 */
@ProviderType
public class DefinitionServiceWrapper implements DefinitionService,
	ServiceWrapper<DefinitionService> {
	public DefinitionServiceWrapper(DefinitionService definitionService) {
		_definitionService = definitionService;
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Definition addDefinition(
		long groupId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, long sourceId,
		String reportParameters, String fileName,
		java.io.InputStream inputStream,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _definitionService.addDefinition(groupId, nameMap,
			descriptionMap, sourceId, reportParameters, fileName, inputStream,
			serviceContext);
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Definition deleteDefinition(
		long definitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _definitionService.deleteDefinition(definitionId);
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Definition getDefinition(
		long definitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _definitionService.getDefinition(definitionId);
	}

	@Override
	public java.util.List<com.liferay.portal.reports.engine.console.model.Definition> getDefinitions(
		long groupId, String definitionName, String description,
		String sourceId, String reportName, boolean andSearch, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _definitionService.getDefinitions(groupId, definitionName,
			description, sourceId, reportName, andSearch, start, end,
			orderByComparator);
	}

	@Override
	public int getDefinitionsCount(long groupId, String definitionName,
		String description, String sourceId, String reportName,
		boolean andSearch) {
		return _definitionService.getDefinitionsCount(groupId, definitionName,
			description, sourceId, reportName, andSearch);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _definitionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Definition updateDefinition(
		long definitionId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, long sourceId,
		String reportParameters, String fileName,
		java.io.InputStream inputStream,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _definitionService.updateDefinition(definitionId, nameMap,
			descriptionMap, sourceId, reportParameters, fileName, inputStream,
			serviceContext);
	}

	@Override
	public DefinitionService getWrappedService() {
		return _definitionService;
	}

	@Override
	public void setWrappedService(DefinitionService definitionService) {
		_definitionService = definitionService;
	}

	private DefinitionService _definitionService;
}