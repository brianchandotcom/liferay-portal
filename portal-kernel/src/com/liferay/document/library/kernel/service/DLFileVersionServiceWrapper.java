/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.service;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFileVersionService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionService
 * @generated
 */
public class DLFileVersionServiceWrapper
	implements DLFileVersionService, ServiceWrapper<DLFileVersionService> {

	public DLFileVersionServiceWrapper() {
		this(null);
	}

	public DLFileVersionServiceWrapper(
		DLFileVersionService dlFileVersionService) {

		_dlFileVersionService = dlFileVersionService;
	}

	@Override
	public DLFileVersion getFileVersion(long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionService.getFileVersion(fileVersionId);
	}

	@Override
	public java.util.List<DLFileVersion> getFileVersions(
			long fileEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionService.getFileVersions(fileEntryId, status);
	}

	@Override
	public int getFileVersionsCount(long fileEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionService.getFileVersionsCount(fileEntryId, status);
	}

	@Override
	public DLFileVersion getLatestFileVersion(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionService.getLatestFileVersion(fileEntryId);
	}

	@Override
	public DLFileVersion getLatestFileVersion(
			long fileEntryId, boolean excludeWorkingCopy)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionService.getLatestFileVersion(
			fileEntryId, excludeWorkingCopy);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dlFileVersionService.getOSGiServiceIdentifier();
	}

	@Override
	public DLFileVersionService getWrappedService() {
		return _dlFileVersionService;
	}

	@Override
	public void setWrappedService(DLFileVersionService dlFileVersionService) {
		_dlFileVersionService = dlFileVersionService;
	}

	private DLFileVersionService _dlFileVersionService;

}