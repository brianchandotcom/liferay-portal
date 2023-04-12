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

package com.liferay.headless.commerce.admin.catalog.internal.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.upload.UniqueFileNameProvider;

import java.io.File;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Stefano Motta
 */
public class FileEntryUtil {

	public static long getFileEntryId(
			String attachment, String url,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		if (Validator.isNotNull(attachment) && Validator.isNull(url)) {
			serviceContext.setExpandoBridgeAttributes(new HashMap<>());

			FileEntry fileEntry = _addFileEntry(
				FileUtil.createTempFile(Base64.decode(attachment)), null,
				uniqueFileNameProvider, serviceContext);

			return fileEntry.getFileEntryId();
		}

		return 0;
	}

	private static FileEntry _addFileEntry(
			File file, String contentType,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		String uniqueFileName = uniqueFileNameProvider.provide(
			file.getName(),
			curFileName -> _exists(
				serviceContext.getScopeGroupId(), serviceContext.getUserId(),
				curFileName));

		if (Validator.isNull(contentType)) {
			contentType = MimeTypesUtil.getContentType(file);
		}

		uniqueFileName = _appendExtension(contentType, uniqueFileName);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			null, serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, uniqueFileName,
			contentType, uniqueFileName, StringPool.BLANK, null,
			StringPool.BLANK, file, null, null, serviceContext);

		FileUtil.delete(file);

		return fileEntry;
	}

	private static String _appendExtension(
		String contentType, String uniqueFileName) {

		String extension = StringPool.BLANK;

		Set<String> extensions = MimeTypesUtil.getExtensions(contentType);

		if (!extensions.isEmpty()) {
			Iterator<String> iterator = extensions.iterator();

			if (iterator.hasNext()) {
				extension = iterator.next();
			}
		}

		return uniqueFileName.concat(extension);
	}

	private static boolean _exists(
		long groupId, long userId, String curFileName) {

		try {
			FileEntry fileEntry = TempFileEntryUtil.getTempFileEntry(
				groupId, userId, _TEMP_FILE_NAME, curFileName);

			if (fileEntry != null) {
				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	private static final String _TEMP_FILE_NAME = FileEntryUtil.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(FileEntryUtil.class);

}