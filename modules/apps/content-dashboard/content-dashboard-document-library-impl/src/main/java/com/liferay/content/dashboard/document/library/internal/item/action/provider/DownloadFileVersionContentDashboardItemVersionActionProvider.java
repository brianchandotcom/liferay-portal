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

package com.liferay.content.dashboard.document.library.internal.item.action.provider;

import com.liferay.content.dashboard.document.library.internal.item.action.DownloadFileVersionContentDashboardItemVersionAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemVersionActionProvider;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "service.ranking:Integer=300",
	service = ContentDashboardItemVersionActionProvider.class
)
public class DownloadFileVersionContentDashboardItemVersionActionProvider
	implements ContentDashboardItemVersionActionProvider<FileVersion> {

	@Override
	public ContentDashboardItemVersionAction
		getContentDashboardItemVersionAction(
			FileVersion fileVersion, HttpServletRequest httpServletRequest) {

		if (!isShow(fileVersion, httpServletRequest)) {
			return null;
		}

		FileEntry fileEntry = _getFileEntry(fileVersion);

		if (fileEntry == null) {
			return null;
		}

		return _getContentDashboardItemVersionAction(fileEntry);
	}

	@Override
	public boolean isShow(
		FileVersion fileVersion, HttpServletRequest httpServletRequest) {

		FileEntry fileEntry = _getFileEntry(fileVersion);

		if ((fileEntry == null) ||
			Objects.equals(
				fileEntry.getMimeType(),
				ContentTypes.
					APPLICATION_VND_LIFERAY_VIDEO_EXTERNAL_SHORTCUT_HTML)) {

			return false;
		}

		ContentDashboardItemVersionAction contentDashboardItemVersionAction =
			_getContentDashboardItemVersionAction(fileEntry);

		if (Validator.isNull(contentDashboardItemVersionAction.getURL())) {
			return false;
		}

		return true;
	}

	private ContentDashboardItemVersionAction
		_getContentDashboardItemVersionAction(FileEntry fileEntry) {

		InfoItemFieldValuesProvider<FileEntry> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, FileEntry.class.getName());

		return new DownloadFileVersionContentDashboardItemVersionAction(
			fileEntry, infoItemFieldValuesProvider, _language);
	}

	private FileEntry _getFileEntry(FileVersion fileVersion) {
		try {
			return fileVersion.getFileEntry();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DownloadFileVersionContentDashboardItemVersionActionProvider.class);

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Language _language;

}