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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for ExportImport. This utility wraps
 * {@link com.liferay.portlet.exportimport.service.impl.ExportImportLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportLocalService
 * @see com.liferay.portlet.exportimport.service.base.ExportImportLocalServiceBaseImpl
 * @see com.liferay.portlet.exportimport.service.impl.ExportImportLocalServiceImpl
 * @generated
 */
@ProviderType
public class ExportImportLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.exportimport.service.impl.ExportImportLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.io.File exportLayoutsAsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().exportLayoutsAsFile(exportImportConfiguration);
	}

	public static long exportLayoutsAsFileInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportLayoutsAsFileInBackground(userId,
			exportImportConfiguration);
	}

	public static long exportLayoutsAsFileInBackground(long userId,
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportLayoutsAsFileInBackground(userId,
			exportImportConfigurationId);
	}

	public static java.io.File exportPortletInfoAsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().exportPortletInfoAsFile(exportImportConfiguration);
	}

	public static long exportPortletInfoAsFileInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportPortletInfoAsFileInBackground(userId,
			exportImportConfiguration);
	}

	public static long exportPortletInfoAsFileInBackground(long userId,
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .exportPortletInfoAsFileInBackground(userId,
			exportImportConfigurationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void importLayouts(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importLayouts(exportImportConfiguration, file);
	}

	public static void importLayouts(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importLayouts(exportImportConfiguration, inputStream);
	}

	public static void importLayoutsDataDeletions(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importLayoutsDataDeletions(exportImportConfiguration, file);
	}

	public static long importLayoutsInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importLayoutsInBackground(userId,
			exportImportConfiguration, file);
	}

	public static long importLayoutsInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importLayoutsInBackground(userId,
			exportImportConfiguration, inputStream);
	}

	public static long importLayoutsInBackground(long userId,
		long exportImportConfigurationId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importLayoutsInBackground(userId,
			exportImportConfigurationId, file);
	}

	public static long importLayoutsInBackground(long userId,
		long exportImportConfigurationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importLayoutsInBackground(userId,
			exportImportConfigurationId, inputStream);
	}

	public static void importPortletDataDeletions(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importPortletDataDeletions(exportImportConfiguration, file);
	}

	public static void importPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importPortletInfo(exportImportConfiguration, file);
	}

	public static void importPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().importPortletInfo(exportImportConfiguration, inputStream);
	}

	public static long importPortletInfoInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importPortletInfoInBackground(userId,
			exportImportConfiguration, file);
	}

	public static long importPortletInfoInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importPortletInfoInBackground(userId,
			exportImportConfiguration, inputStream);
	}

	public static long importPortletInfoInBackground(long userId,
		long exportImportConfigurationId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importPortletInfoInBackground(userId,
			exportImportConfigurationId, file);
	}

	public static long importPortletInfoInBackground(long userId,
		long exportImportConfigurationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .importPortletInfoInBackground(userId,
			exportImportConfigurationId, inputStream);
	}

	public static com.liferay.portlet.exportimport.lar.MissingReferences validateImportLayoutsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportLayoutsFile(exportImportConfiguration, file);
	}

	public static com.liferay.portlet.exportimport.lar.MissingReferences validateImportLayoutsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportLayoutsFile(exportImportConfiguration,
			inputStream);
	}

	public static com.liferay.portlet.exportimport.lar.MissingReferences validateImportPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportPortletInfo(exportImportConfiguration, file);
	}

	public static com.liferay.portlet.exportimport.lar.MissingReferences validateImportPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateImportPortletInfo(exportImportConfiguration,
			inputStream);
	}

	public static ExportImportLocalService getService() {
		if (_service == null) {
			_service = (ExportImportLocalService)PortalBeanLocatorUtil.locate(ExportImportLocalService.class.getName());

			ReferenceRegistry.registerReference(ExportImportLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static ExportImportLocalService _service;
}