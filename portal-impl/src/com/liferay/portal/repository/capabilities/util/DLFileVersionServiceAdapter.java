/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.capabilities.util;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionService;
import com.liferay.document.library.kernel.service.DLFileVersionServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;

/**
 * @author Iván Zaera
 */
public class DLFileVersionServiceAdapter {

	public static DLFileVersionServiceAdapter create(
		DocumentRepository documentRepository) {

		if (documentRepository instanceof LocalRepository) {
			return new DLFileVersionServiceAdapter(
				DLFileVersionLocalServiceUtil.getService());
		}

		return new DLFileVersionServiceAdapter(
			DLFileVersionLocalServiceUtil.getService(),
			DLFileVersionServiceUtil.getService());
	}

	public DLFileVersionServiceAdapter(
		DLFileVersionLocalService dlFileVersionLocalService) {

		this(dlFileVersionLocalService, null);
	}

	public DLFileVersionServiceAdapter(
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService) {

		_dlFileVersionLocalService = dlFileVersionLocalService;
		_dlFileVersionService = dlFileVersionService;
	}

	public DLFileVersion getLatestFileVersion(
			long fileEntryId, boolean excludeWorkingCopy)
		throws PortalException {

		DLFileVersion dlFileVersion = null;

		if (_dlFileVersionService != null) {
			dlFileVersion = _dlFileVersionService.getLatestFileVersion(
				fileEntryId, excludeWorkingCopy);
		}
		else {
			dlFileVersion = _dlFileVersionLocalService.getLatestFileVersion(
				fileEntryId, excludeWorkingCopy);
		}

		return dlFileVersion;
	}

	private final DLFileVersionLocalService _dlFileVersionLocalService;
	private final DLFileVersionService _dlFileVersionService;

}