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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

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

	private final DLAppLocalService _dlAppLocalService;
	private final DLAppService _dlAppService;

}