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