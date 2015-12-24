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

package com.liferay.portlet.exportimport.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExportImportConfigurationService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportConfigurationService
 * @generated
 */
@ProviderType
public class ExportImportConfigurationServiceWrapper
	implements ExportImportConfigurationService,
		ServiceWrapper<ExportImportConfigurationService> {
	public ExportImportConfigurationServiceWrapper(
		ExportImportConfigurationService exportImportConfigurationService) {
		_exportImportConfigurationService = exportImportConfigurationService;
	}

	@Override
	public void deleteExportImportConfiguration(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportConfigurationService.deleteExportImportConfiguration(exportImportConfigurationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _exportImportConfigurationService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration moveExportImportConfigurationToTrash(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportConfigurationService.moveExportImportConfigurationToTrash(exportImportConfigurationId);
	}

	@Override
	public com.liferay.portlet.exportimport.model.ExportImportConfiguration restoreExportImportConfigurationFromTrash(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportConfigurationService.restoreExportImportConfigurationFromTrash(exportImportConfigurationId);
	}

	@Override
	public ExportImportConfigurationService getWrappedService() {
		return _exportImportConfigurationService;
	}

	@Override
	public void setWrappedService(
		ExportImportConfigurationService exportImportConfigurationService) {
		_exportImportConfigurationService = exportImportConfigurationService;
	}

	private ExportImportConfigurationService _exportImportConfigurationService;
}