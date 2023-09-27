/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.service;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for ExportImportConfiguration. This utility wraps
 * <code>com.liferay.portlet.exportimport.service.impl.ExportImportConfigurationServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportConfigurationService
 * @generated
 */
public class ExportImportConfigurationServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.exportimport.service.impl.ExportImportConfigurationServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteExportImportConfiguration(
			long exportImportConfigurationId)
		throws PortalException {

		getService().deleteExportImportConfiguration(
			exportImportConfigurationId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ExportImportConfiguration
			moveExportImportConfigurationToTrash(
				long exportImportConfigurationId)
		throws PortalException {

		return getService().moveExportImportConfigurationToTrash(
			exportImportConfigurationId);
	}

	public static ExportImportConfiguration
			restoreExportImportConfigurationFromTrash(
				long exportImportConfigurationId)
		throws PortalException {

		return getService().restoreExportImportConfigurationFromTrash(
			exportImportConfigurationId);
	}

	public static ExportImportConfigurationService getService() {
		return _service;
	}

	public static void setService(ExportImportConfigurationService service) {
		_service = service;
	}

	private static volatile ExportImportConfigurationService _service;

}