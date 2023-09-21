/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.upload;

import com.liferay.blogs.configuration.BlogsFileUploadsConfiguration;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.upload.UniqueFileNameProvider;

import java.io.InputStream;

/**
 * @author Alejandro Tardín
 */
public class TempImageBlogsUploadFileEntryHandler
	extends ImageBlogsUploadFileEntryHandler {

	public TempImageBlogsUploadFileEntryHandler(
		BlogsEntryLocalService blogsLocalService,
		BlogsFileUploadsConfiguration blogsFileUploadsConfiguration,
		PortletFileRepository portletFileRepository,
		PortletResourcePermission portletResourcePermission,
		UniqueFileNameProvider uniqueFileNameProvider) {

		super(
			blogsLocalService, blogsFileUploadsConfiguration,
			portletFileRepository, portletResourcePermission);

		_uniqueFileNameProvider = uniqueFileNameProvider;
	}

	@Override
	protected FileEntry addFileEntry(
			String fileName, String contentType, InputStream inputStream,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String uniqueFileName = _uniqueFileNameProvider.provide(
			fileName, curFileName -> _exists(themeDisplay, curFileName));

		return TempFileEntryUtil.addTempFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			_TEMP_FOLDER_NAME, uniqueFileName, inputStream, contentType);
	}

	private boolean _exists(ThemeDisplay themeDisplay, String curFileName) {
		try {
			FileEntry tempFileEntry = TempFileEntryUtil.getTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				_TEMP_FOLDER_NAME, curFileName);

			if (tempFileEntry != null) {
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

	private static final String _TEMP_FOLDER_NAME =
		TempImageBlogsUploadFileEntryHandler.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		TempImageBlogsUploadFileEntryHandler.class);

	private final UniqueFileNameProvider _uniqueFileNameProvider;

}