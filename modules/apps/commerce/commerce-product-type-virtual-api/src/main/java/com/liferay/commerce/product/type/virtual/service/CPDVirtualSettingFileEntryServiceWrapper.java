/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDVirtualSettingFileEntryService}.
 *
 * @author Marco Leo
 * @see CPDVirtualSettingFileEntryService
 * @generated
 */
public class CPDVirtualSettingFileEntryServiceWrapper
	implements CPDVirtualSettingFileEntryService,
			   ServiceWrapper<CPDVirtualSettingFileEntryService> {

	public CPDVirtualSettingFileEntryServiceWrapper() {
		this(null);
	}

	public CPDVirtualSettingFileEntryServiceWrapper(
		CPDVirtualSettingFileEntryService cpdVirtualSettingFileEntryService) {

		_cpdVirtualSettingFileEntryService = cpdVirtualSettingFileEntryService;
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry addCPDefinitionVirtualSetting(
					long groupId, String className, long classPK,
					long cpDefinitionVirtualSettingId, long fileEntryId,
					String url, String version)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpdVirtualSettingFileEntryService.addCPDefinitionVirtualSetting(
			groupId, className, classPK, cpDefinitionVirtualSettingId,
			fileEntryId, url, version);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry addFileEntry(
			long groupId, long folderId, java.io.InputStream inputStream,
			String fileName, String mimeType, String serviceName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpdVirtualSettingFileEntryService.addFileEntry(
			groupId, folderId, inputStream, fileName, mimeType, serviceName);
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry deleteCPDVirtualSettingFileEntry(
					long cpdVirtualSettingFileEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpdVirtualSettingFileEntryService.
			deleteCPDVirtualSettingFileEntry(cpdVirtualSettingFileEntryId);
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry deleteCPDVirtualSettingFileEntry(
					String className, long classPK,
					long cpdVirtualSettingFileEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpdVirtualSettingFileEntryService.
			deleteCPDVirtualSettingFileEntry(
				className, classPK, cpdVirtualSettingFileEntryId);
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry fetchCPDVirtualSettingFileEntry(
					long cpdVirtualSettingFileEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpdVirtualSettingFileEntryService.
			fetchCPDVirtualSettingFileEntry(cpdVirtualSettingFileEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry> getCPDVirtualSettingFileEntries(
					String className, long classPK,
					long cpDefinitionVirtualSettingId, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpdVirtualSettingFileEntryService.
			getCPDVirtualSettingFileEntries(
				className, classPK, cpDefinitionVirtualSettingId, start, end);
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry getCPDVirtualSettingFileEntry(
					long cpdVirtualSettingFileEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpdVirtualSettingFileEntryService.getCPDVirtualSettingFileEntry(
			cpdVirtualSettingFileEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpdVirtualSettingFileEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry updateCPDefinitionVirtualSetting(
					long cpdVirtualSettingFileEntryId, long fileEntryId,
					String url, String version)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpdVirtualSettingFileEntryService.
			updateCPDefinitionVirtualSetting(
				cpdVirtualSettingFileEntryId, fileEntryId, url, version);
	}

	@Override
	public CPDVirtualSettingFileEntryService getWrappedService() {
		return _cpdVirtualSettingFileEntryService;
	}

	@Override
	public void setWrappedService(
		CPDVirtualSettingFileEntryService cpdVirtualSettingFileEntryService) {

		_cpdVirtualSettingFileEntryService = cpdVirtualSettingFileEntryService;
	}

	private CPDVirtualSettingFileEntryService
		_cpdVirtualSettingFileEntryService;

}