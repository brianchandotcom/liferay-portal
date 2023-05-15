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

package com.liferay.exportimport.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.InputStream;

/**
 * Provides the remote service utility for ExportImport. This utility wraps
 * <code>com.liferay.portlet.exportimport.service.impl.ExportImportServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportService
 * @generated
 */
public class ExportImportServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.exportimport.service.impl.ExportImportServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.io.File exportLayoutsAsFile(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws PortalException {

		return getService().exportLayoutsAsFile(exportImportConfiguration);
	}

	public static long exportLayoutsAsFileInBackground(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws PortalException {

		return getService().exportLayoutsAsFileInBackground(
			exportImportConfiguration);
	}

	public static long exportLayoutsAsFileInBackground(
			long exportImportConfigurationId)
		throws PortalException {

		return getService().exportLayoutsAsFileInBackground(
			exportImportConfigurationId);
	}

	public static java.io.File exportPortletInfoAsFile(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws PortalException {

		return getService().exportPortletInfoAsFile(exportImportConfiguration);
	}

	public static long exportPortletInfoAsFileInBackground(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws PortalException {

		return getService().exportPortletInfoAsFileInBackground(
			exportImportConfiguration);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void importLayouts(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws PortalException {

		getService().importLayouts(exportImportConfiguration, file);
	}

	public static void importLayouts(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		getService().importLayouts(exportImportConfiguration, inputStream);
	}

	public static long importLayoutsInBackground(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws PortalException {

		return getService().importLayoutsInBackground(
			exportImportConfiguration, file);
	}

	public static long importLayoutsInBackground(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		return getService().importLayoutsInBackground(
			exportImportConfiguration, inputStream);
	}

	public static void importPortletInfo(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws PortalException {

		getService().importPortletInfo(exportImportConfiguration, file);
	}

	public static void importPortletInfo(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		getService().importPortletInfo(exportImportConfiguration, inputStream);
	}

	public static long importPortletInfoInBackground(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws PortalException {

		return getService().importPortletInfoInBackground(
			exportImportConfiguration, file);
	}

	public static long importPortletInfoInBackground(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			InputStream inputStream)
		throws PortalException {

		return getService().importPortletInfoInBackground(
			exportImportConfiguration, inputStream);
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportLayoutsFile(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.File file)
		throws PortalException {

		return getService().validateImportLayoutsFile(
			exportImportConfiguration, file);
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportLayoutsFile(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				InputStream inputStream)
		throws PortalException {

		return getService().validateImportLayoutsFile(
			exportImportConfiguration, inputStream);
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportPortletInfo(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.File file)
		throws PortalException {

		return getService().validateImportPortletInfo(
			exportImportConfiguration, file);
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportPortletInfo(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				InputStream inputStream)
		throws PortalException {

		return getService().validateImportPortletInfo(
			exportImportConfiguration, inputStream);
	}

	public static ExportImportService getService() {
		return _service;
	}

	public static void setService(ExportImportService service) {
		_service = service;
	}

	private static volatile ExportImportService _service;

}