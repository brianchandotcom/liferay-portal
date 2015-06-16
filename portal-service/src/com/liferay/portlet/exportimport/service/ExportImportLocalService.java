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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;

/**
 * Provides the local service interface for ExportImport. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportLocalServiceUtil
 * @see com.liferay.portlet.exportimport.service.base.ExportImportLocalServiceBaseImpl
 * @see com.liferay.portlet.exportimport.service.impl.ExportImportLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ExportImportLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExportImportLocalServiceUtil} to access the export import local service. Add custom service methods to {@link com.liferay.portlet.exportimport.service.impl.ExportImportLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public java.io.File exportLayoutsAsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public long exportLayoutsAsFileInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public long exportLayoutsAsFileInBackground(long userId,
		long exportImportConfigurationId) throws PortalException;

	public java.io.File exportPortletInfoAsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public long exportPortletInfoAsFileInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	public long exportPortletInfoAsFileInBackground(long userId,
		long exportImportConfigurationId) throws PortalException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	public void importLayouts(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file) throws PortalException;

	public void importLayouts(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream) throws PortalException;

	public void importLayoutsDataDeletions(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file) throws PortalException;

	public long importLayoutsInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file) throws PortalException;

	public long importLayoutsInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream) throws PortalException;

	public long importLayoutsInBackground(long userId,
		long exportImportConfigurationId, java.io.File file)
		throws PortalException;

	public long importLayoutsInBackground(long userId,
		long exportImportConfigurationId, java.io.InputStream inputStream)
		throws PortalException;

	public void importPortletDataDeletions(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file) throws PortalException;

	public void importPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file) throws PortalException;

	public void importPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream) throws PortalException;

	public long importPortletInfoInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file) throws PortalException;

	public long importPortletInfoInBackground(long userId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream) throws PortalException;

	public long importPortletInfoInBackground(long userId,
		long exportImportConfigurationId, java.io.File file)
		throws PortalException;

	public long importPortletInfoInBackground(long userId,
		long exportImportConfigurationId, java.io.InputStream inputStream)
		throws PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public com.liferay.portlet.exportimport.lar.MissingReferences validateImportLayoutsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file) throws PortalException;

	public com.liferay.portlet.exportimport.lar.MissingReferences validateImportLayoutsFile(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream) throws PortalException;

	public com.liferay.portlet.exportimport.lar.MissingReferences validateImportPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file) throws PortalException;

	public com.liferay.portlet.exportimport.lar.MissingReferences validateImportPortletInfo(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream) throws PortalException;
}