/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.capabilities.util;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;

/**
 * @author Iván Zaera
 */
public class DLAppServiceAdapter {

	public static DLAppServiceAdapter create(
		DocumentRepository documentRepository) {

		if (documentRepository instanceof LocalRepository) {
			return new DLAppServiceAdapter(DLAppLocalServiceUtil.getService());
		}

		return new DLAppServiceAdapter(
			DLAppLocalServiceUtil.getService(), DLAppServiceUtil.getService());
	}

	public DLAppServiceAdapter(DLAppLocalService dlAppLocalService) {
		this(dlAppLocalService, null);
	}

	public DLAppServiceAdapter(
		DLAppLocalService dlAppLocalService, DLAppService dlAppService) {

		_dlAppLocalService = dlAppLocalService;
		_dlAppService = dlAppService;
	}

	public void deleteFileEntry(long fileEntryId) throws PortalException {
		if (_dlAppService != null) {
			_dlAppService.deleteFileEntry(fileEntryId);
		}
		else {
			_dlAppLocalService.deleteFileEntry(fileEntryId);
		}
	}

	public void deleteFileVersion(long fileVersionId) throws PortalException {
		if (_dlAppService != null) {
			_dlAppService.deleteFileVersion(fileVersionId);
		}
		else {
			_dlAppLocalService.deleteFileVersion(fileVersionId);
		}
	}

	private final DLAppLocalService _dlAppLocalService;
	private final DLAppService _dlAppService;

}