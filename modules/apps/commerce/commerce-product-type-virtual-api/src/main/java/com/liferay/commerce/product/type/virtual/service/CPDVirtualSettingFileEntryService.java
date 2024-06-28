/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.service;

import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.InputStream;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CPDVirtualSettingFileEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPDVirtualSettingFileEntryServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CPDVirtualSettingFileEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.product.type.virtual.service.impl.CPDVirtualSettingFileEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the cpd virtual setting file entry remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CPDVirtualSettingFileEntryServiceUtil} if injection and service tracking are not available.
	 */
	public CPDVirtualSettingFileEntry addCPDefinitionVirtualSetting(
			long groupId, String className, long classPK,
			long cpDefinitionVirtualSettingId, long fileEntryId, String url,
			String version)
		throws PortalException;

	public FileEntry addFileEntry(
			long groupId, long folderId, InputStream inputStream,
			String fileName, String mimeType, String serviceName)
		throws PortalException;

	public CPDVirtualSettingFileEntry deleteCPDVirtualSettingFileEntry(
			long cpdVirtualSettingFileEntryId)
		throws PortalException;

	public CPDVirtualSettingFileEntry deleteCPDVirtualSettingFileEntry(
			String className, long classPK, long cpdVirtualSettingFileEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDVirtualSettingFileEntry fetchCPDVirtualSettingFileEntry(
			long cpdVirtualSettingFileEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDVirtualSettingFileEntry> getCPDVirtualSettingFileEntries(
			String className, long classPK, long cpDefinitionVirtualSettingId,
			int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDVirtualSettingFileEntry getCPDVirtualSettingFileEntry(
			long cpdVirtualSettingFileEntryId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CPDVirtualSettingFileEntry updateCPDefinitionVirtualSetting(
			long cpdVirtualSettingFileEntryId, long fileEntryId, String url,
			String version)
		throws PortalException;

}