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

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _exportImportConfigurationService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_exportImportConfigurationService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public void deleteExportImportConfiguration(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportConfigurationService.deleteExportImportConfiguration(exportImportConfigurationId);
	}

	@Override
	public com.liferay.portal.model.ExportImportConfiguration moveExportImportConfigurationToTrash(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportConfigurationService.moveExportImportConfigurationToTrash(exportImportConfigurationId);
	}

	@Override
	public com.liferay.portal.model.ExportImportConfiguration restoreExportImportConfigurationFromTrash(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportConfigurationService.restoreExportImportConfigurationFromTrash(exportImportConfigurationId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ExportImportConfigurationService getWrappedExportImportConfigurationService() {
		return _exportImportConfigurationService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedExportImportConfigurationService(
		ExportImportConfigurationService exportImportConfigurationService) {
		_exportImportConfigurationService = exportImportConfigurationService;
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