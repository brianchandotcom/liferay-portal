/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.service;

import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.File;
import java.io.InputStream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for ExportImport. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ExportImportService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portlet.exportimport.service.impl.ExportImportServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the export import remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ExportImportServiceUtil} if injection and service tracking are not available.
	 */
	@CTAware
	public File exportLayoutsAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	@CTAware
	public long exportLayoutsAsFileInBackground(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	@CTAware
	public long exportLayoutsAsFileInBackground(
			long exportImportConfigurationId)
		throws PortalException;

	@CTAware
	public File exportPortletInfoAsFile(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	@CTAware
	public long exportPortletInfoAsFileInBackground(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@CTAware
	public void importLayouts(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	@CTAware
	public void importLayouts(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException;

	@CTAware
	public long importLayoutsInBackground(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	@CTAware
	public long importLayoutsInBackground(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException;

	@CTAware
	public void importPortletInfo(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	@CTAware
	public void importPortletInfo(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException;

	@CTAware
	public long importPortletInfoInBackground(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	@CTAware
	public long importPortletInfoInBackground(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException;

	@CTAware
	public MissingReferences validateImportLayoutsFile(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	@CTAware
	public MissingReferences validateImportLayoutsFile(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException;

	@CTAware
	public MissingReferences validateImportPortletInfo(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException;

	@CTAware
	public MissingReferences validateImportPortletInfo(
			ExportImportConfiguration exportImportConfiguration,
			InputStream inputStream)
		throws PortalException;

}