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

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.File;
import java.io.InputStream;

/**
 * Provides the remote service interface for ExportImport. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportServiceUtil
 * @see com.liferay.portlet.exportimport.service.base.ExportImportServiceBaseImpl
 * @see com.liferay.portlet.exportimport.service.impl.ExportImportServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ExportImportService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExportImportServiceUtil} to access the export import remote service. Add custom service methods to {@link com.liferay.portlet.exportimport.service.impl.ExportImportServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public File exportLayoutsAsFile(
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public long exportLayoutsAsFileInBackground(
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public long exportLayoutsAsFileInBackground(
		long exportImportConfigurationId) throws PortalException;

	public File exportPortletInfoAsFile(
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public long exportPortletInfoAsFileInBackground(
		ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public void importLayouts(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	public void importLayouts(
		ExportImportConfiguration exportImportConfiguration,
		InputStream inputStream) throws PortalException;

	public long importLayoutsInBackground(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	public long importLayoutsInBackground(
		ExportImportConfiguration exportImportConfiguration,
		InputStream inputStream) throws PortalException;

	public void importPortletInfo(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	public void importPortletInfo(
		ExportImportConfiguration exportImportConfiguration,
		InputStream inputStream) throws PortalException;

	public long importPortletInfoInBackground(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	public long importPortletInfoInBackground(
		ExportImportConfiguration exportImportConfiguration,
		InputStream inputStream) throws PortalException;

	public MissingReferences validateImportLayoutsFile(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	public MissingReferences validateImportLayoutsFile(
		ExportImportConfiguration exportImportConfiguration,
		InputStream inputStream) throws PortalException;

	public MissingReferences validateImportPortletInfo(
		ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	public MissingReferences validateImportPortletInfo(
		ExportImportConfiguration exportImportConfiguration,
		InputStream inputStream) throws PortalException;
}