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
 * Provides a wrapper for {@link ExportImportLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportLocalService
 * @generated
 */
@ProviderType
public class ExportImportLocalServiceWrapper implements ExportImportLocalService,
	ServiceWrapper<ExportImportLocalService> {
	public ExportImportLocalServiceWrapper(
		ExportImportLocalService exportImportLocalService) {
		_exportImportLocalService = exportImportLocalService;
	}

	@Override
	public java.io.File exportLayoutsAsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportLayoutsAsFile(exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportLayoutsAsFileInBackground(userId,
			exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(long userId,
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportLayoutsAsFileInBackground(userId,
			exportImportConfigurationId);
	}

	@Override
	public java.io.File exportPortletInfoAsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportPortletInfoAsFile(exportImportConfiguration);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportPortletInfoAsFileInBackground(userId,
			exportImportConfiguration);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(long userId,
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.exportPortletInfoAsFileInBackground(userId,
			exportImportConfigurationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _exportImportLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public void importLayouts(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importLayouts(exportImportConfiguration, file);
	}

	@Override
	public void importLayouts(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importLayouts(exportImportConfiguration,
			inputStream);
	}

	@Override
	public void importLayoutsDataDeletions(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importLayoutsDataDeletions(exportImportConfiguration,
			file);
	}

	@Override
	public long importLayoutsInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importLayoutsInBackground(userId,
			exportImportConfiguration, file);
	}

	@Override
	public long importLayoutsInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importLayoutsInBackground(userId,
			exportImportConfiguration, inputStream);
	}

	@Override
	public long importLayoutsInBackground(long userId,
		long exportImportConfigurationId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importLayoutsInBackground(userId,
			exportImportConfigurationId, file);
	}

	@Override
	public long importLayoutsInBackground(long userId,
		long exportImportConfigurationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importLayoutsInBackground(userId,
			exportImportConfigurationId, inputStream);
	}

	@Override
	public void importPortletDataDeletions(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importPortletDataDeletions(exportImportConfiguration,
			file);
	}

	@Override
	public void importPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importPortletInfo(exportImportConfiguration,
			file);
	}

	@Override
	public void importPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		_exportImportLocalService.importPortletInfo(exportImportConfiguration,
			inputStream);
	}

	@Override
	public long importPortletInfoInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importPortletInfoInBackground(userId,
			exportImportConfiguration, file);
	}

	@Override
	public long importPortletInfoInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importPortletInfoInBackground(userId,
			exportImportConfiguration, inputStream);
	}

	@Override
	public long importPortletInfoInBackground(long userId,
		long exportImportConfigurationId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importPortletInfoInBackground(userId,
			exportImportConfigurationId, file);
	}

	@Override
	public long importPortletInfoInBackground(long userId,
		long exportImportConfigurationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.importPortletInfoInBackground(userId,
			exportImportConfigurationId, inputStream);
	}

	@Override
	public com.liferay.portlet.exportimport.lar.MissingReferences validateImportLayoutsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.validateImportLayoutsFile(exportImportConfiguration,
			file);
	}

	@Override
	public com.liferay.portlet.exportimport.lar.MissingReferences validateImportLayoutsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.validateImportLayoutsFile(exportImportConfiguration,
			inputStream);
	}

	@Override
	public com.liferay.portlet.exportimport.lar.MissingReferences validateImportPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.validateImportPortletInfo(exportImportConfiguration,
			file);
	}

	@Override
	public com.liferay.portlet.exportimport.lar.MissingReferences validateImportPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _exportImportLocalService.validateImportPortletInfo(exportImportConfiguration,
			inputStream);
	}

	@Override
	public ExportImportLocalService getWrappedService() {
		return _exportImportLocalService;
	}

	@Override
	public void setWrappedService(
		ExportImportLocalService exportImportLocalService) {
		_exportImportLocalService = exportImportLocalService;
	}

	private ExportImportLocalService _exportImportLocalService;
}