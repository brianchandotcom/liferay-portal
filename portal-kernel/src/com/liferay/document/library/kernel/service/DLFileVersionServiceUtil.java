/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.service;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for DLFileVersion. This utility wraps
 * <code>com.liferay.portlet.documentlibrary.service.impl.DLFileVersionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionService
 * @generated
 */
public class DLFileVersionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.documentlibrary.service.impl.DLFileVersionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DLFileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		return getService().getFileVersion(fileVersionId);
	}

	public static List<DLFileVersion> getFileVersions(
			long fileEntryId, int status)
		throws PortalException {

		return getService().getFileVersions(fileEntryId, status);
	}

	public static int getFileVersionsCount(long fileEntryId, int status)
		throws PortalException {

		return getService().getFileVersionsCount(fileEntryId, status);
	}

	public static DLFileVersion getLatestFileVersion(long fileEntryId)
		throws PortalException {

		return getService().getLatestFileVersion(fileEntryId);
	}

	public static DLFileVersion getLatestFileVersion(
			long fileEntryId, boolean excludeWorkingCopy)
		throws PortalException {

		return getService().getLatestFileVersion(
			fileEntryId, excludeWorkingCopy);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DLFileVersionService getService() {
		return _service;
	}

	public static void setService(DLFileVersionService service) {
		_service = service;
	}

	private static volatile DLFileVersionService _service;

}