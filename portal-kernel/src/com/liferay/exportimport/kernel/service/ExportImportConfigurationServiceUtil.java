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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

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
		throws com.liferay.portal.kernel.exception.PortalException {

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

	public static
		com.liferay.exportimport.kernel.model.ExportImportConfiguration
				moveExportImportConfigurationToTrash(
					long exportImportConfigurationId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveExportImportConfigurationToTrash(
			exportImportConfigurationId);
	}

	public static
		com.liferay.exportimport.kernel.model.ExportImportConfiguration
				restoreExportImportConfigurationFromTrash(
					long exportImportConfigurationId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().restoreExportImportConfigurationFromTrash(
			exportImportConfigurationId);
	}

	public static ExportImportConfigurationService getService() {
		if (_service == null) {
			_service =
				(ExportImportConfigurationService)PortalBeanLocatorUtil.locate(
					ExportImportConfigurationService.class.getName());
		}

		return _service;
	}

	private static ExportImportConfigurationService _service;

}