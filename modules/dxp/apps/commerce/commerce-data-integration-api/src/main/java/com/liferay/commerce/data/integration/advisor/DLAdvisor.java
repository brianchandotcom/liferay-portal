/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.advisor;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.MimeTypesUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author guywandji
 */
public class DLAdvisor {

	public static DLFileEntry addOrUpdateFile(
			long folderId, long fileEntryId, String fileName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		long groupId = serviceContext.getScopeGroupId();

		DLFileEntry fileEntry = null;

		if (fileEntryId == 0) {
			fileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
				serviceContext.getUserId(), groupId, groupId, folderId,
				fileName, mimeType, fileName, fileName, null, 0, null, null,
				inputStream, 0, serviceContext);
		}
		else {
			fileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(
				serviceContext.getUserId(), fileEntryId, fileName, mimeType,
				fileName, fileName, StringPool.BLANK,
				DLVersionNumberIncrease.fromMajorVersion(true), 0L, null, null,
				inputStream, 0L, serviceContext);
		}

		return fileEntry;
	}

	public static DLFileEntry addOrUpdateFile(
			long fileEntryId, String processName, File file)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Folder folder = _getOrCreateFolder(
			serviceContext.getScopeGroupId(), 0, "PROCESS_" + processName,
			serviceContext);

		String mimeType = MimeTypesUtil.getContentType(file);
		String sourceFileName = file.getName();
		long groupId = serviceContext.getScopeGroupId();
		DLFileEntry fileEntry = null;

		if (fileEntryId == 0) {
			fileEntry = DLFileEntryLocalServiceUtil.addFileEntry(
				serviceContext.getUserId(), groupId, groupId,
				folder.getFolderId(), sourceFileName, mimeType, sourceFileName,
				sourceFileName, null, 0, null, file, null, 0, serviceContext);
		}
		else {
			fileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(
				serviceContext.getUserId(), fileEntryId, sourceFileName,
				mimeType, sourceFileName, sourceFileName, StringPool.BLANK,
				DLVersionNumberIncrease.fromMajorVersion(true), 0L, null, file,
				null, 0L, serviceContext);
		}

		return fileEntry;
	}

	private static Folder _getOrCreateFolder(
			long repositoryId, long parentFolderId, String folderName,
			ServiceContext serviceContext)
		throws PortalException {

		Folder folder = null;

		try {
			folder = DLAppLocalServiceUtil.getFolder(
				repositoryId, parentFolderId, folderName);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			folder = null;
		}

		if (folder == null) {
			folder = DLAppLocalServiceUtil.addFolder(
				serviceContext.getUserId(), repositoryId, parentFolderId,
				folderName, "", serviceContext);
		}

		return folder;
	}

	private static final Log _log = LogFactoryUtil.getLog(DLAdvisor.class);

}