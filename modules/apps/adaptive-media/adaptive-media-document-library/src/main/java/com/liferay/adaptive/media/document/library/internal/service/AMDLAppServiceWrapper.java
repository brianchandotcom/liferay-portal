/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.document.library.internal.service;

import com.liferay.adaptive.media.document.library.internal.util.AMCleanUpOnUpdateAndCheckInThreadLocal;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class AMDLAppServiceWrapper extends DLAppServiceWrapper {

	@Override
	public FileEntry updateFileEntryAndCheckIn(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String urlTitle, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, File file,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		return AMCleanUpOnUpdateAndCheckInThreadLocal.enable(
			() -> super.updateFileEntryAndCheckIn(
				fileEntryId, sourceFileName, mimeType, title, urlTitle,
				description, changeLog, dlVersionNumberIncrease, file,
				expirationDate, reviewDate, serviceContext));
	}

	@Override
	public FileEntry updateFileEntryAndCheckIn(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String urlTitle, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		return AMCleanUpOnUpdateAndCheckInThreadLocal.enable(
			() -> super.updateFileEntryAndCheckIn(
				fileEntryId, sourceFileName, mimeType, title, urlTitle,
				description, changeLog, dlVersionNumberIncrease, inputStream,
				size, expirationDate, reviewDate, serviceContext));
	}

	@Reference
	private DLAppService _dlAppService;

}