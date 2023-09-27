/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExportImportLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportLocalService
 * @generated
 */
public class ExportImportLocalServiceWrapper
	implements ExportImportLocalService,
			   ServiceWrapper<ExportImportLocalService> {

	public ExportImportLocalServiceWrapper() {
		this(null);
	}

	public ExportImportLocalServiceWrapper(
		ExportImportLocalService exportImportLocalService) {

		_exportImportLocalService = exportImportLocalService;
	}

	@Override
	public java.io.File exportLayoutsAsFile(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportLayoutsAsFileInBackground(
			userId, exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			long userId, long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportLayoutsAsFileInBackground(
			userId, exportImportConfigurationId);
	}

	@Override
	public java.io.File exportPortletInfoAsFile(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportPortletInfoAsFile(
			exportImportConfiguration);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportPortletInfoAsFileInBackground(
			userId, exportImportConfiguration);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId, long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportPortletInfoAsFileInBackground(
			userId, exportImportConfigurationId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _exportImportLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public void importLayouts(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, file);
	}

	@Override
	public void importLayouts(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, inputStream);
	}

	@Override
	public void importLayoutsDataDeletions(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importLayoutsDataDeletions(
			exportImportConfiguration, file);
	}

	@Override
	public long importLayoutSetPrototypeInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importLayoutSetPrototypeInBackground(
			userId, exportImportConfiguration, file);
	}

	@Override
	public long importLayoutsInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importLayoutsInBackground(
			userId, exportImportConfiguration, file);
	}

	@Override
	public long importLayoutsInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importLayoutsInBackground(
			userId, exportImportConfiguration, inputStream);
	}

	@Override
	public long importLayoutsInBackground(
			long userId, long exportImportConfigurationId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importLayoutsInBackground(
			userId, exportImportConfigurationId, file);
	}

	@Override
	public long importLayoutsInBackground(
			long userId, long exportImportConfigurationId,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importLayoutsInBackground(
			userId, exportImportConfigurationId, inputStream);
	}

	@Override
	public void importPortletDataDeletions(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importPortletDataDeletions(
			exportImportConfiguration, file);
	}

	@Override
	public void importPortletInfo(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importPortletInfo(
			exportImportConfiguration, file);
	}

	@Override
	public void importPortletInfo(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importPortletInfo(
			exportImportConfiguration, inputStream);
	}

	@Override
	public long importPortletInfoInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importPortletInfoInBackground(
			userId, exportImportConfiguration, file);
	}

	@Override
	public long importPortletInfoInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importPortletInfoInBackground(
			userId, exportImportConfiguration, inputStream);
	}

	@Override
	public long importPortletInfoInBackground(
			long userId, long exportImportConfigurationId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importPortletInfoInBackground(
			userId, exportImportConfigurationId, file);
	}

	@Override
	public long importPortletInfoInBackground(
			long userId, long exportImportConfigurationId,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importPortletInfoInBackground(
			userId, exportImportConfigurationId, inputStream);
	}

	@Override
	public long mergeLayoutSetPrototypeInBackground(
			long userId, long groupId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.mergeLayoutSetPrototypeInBackground(
			userId, groupId, exportImportConfiguration);
	}

	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportLayoutsFile(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.validateImportLayoutsFile(
			exportImportConfiguration, file);
	}

	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportLayoutsFile(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.validateImportLayoutsFile(
			exportImportConfiguration, inputStream);
	}

	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportPortletInfo(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.validateImportPortletInfo(
			exportImportConfiguration, file);
	}

	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportPortletInfo(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.validateImportPortletInfo(
			exportImportConfiguration, inputStream);
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