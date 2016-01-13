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

package com.liferay.document.library.web.portlet.configuration.icon;

import com.liferay.document.library.web.display.context.logic.FileEntryDisplayContextHelper;
import com.liferay.document.library.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import javax.portlet.PortletRequest;

/**
 * @author Roberto Díaz
 */
public class DownloadFileEntryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public DownloadFileEntryPortletConfigurationIcon(
		PortletRequest portletRequest, FileEntry fileEntry) {

		super(portletRequest);

		_fileEntry = fileEntry;
	}

	@Override
	public String getMessage() {
		return "download";
	}

	@Override
	public String getMethod() {
		return "get";
	}

	@Override
	public String getURL() {
		String url = StringPool.BLANK;

		try {
			FileVersion fileVersion = ActionUtil.getFileVersion(
				portletRequest, _fileEntry);

			url = DLUtil.getDownloadURL(
				_fileEntry, fileVersion, themeDisplay, StringPool.BLANK);
		}
		catch (Exception e) {
		}

		return url;
	}

	@Override
	public boolean isShow() {
		try {
			FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
				new FileEntryDisplayContextHelper(
					themeDisplay.getPermissionChecker(), _fileEntry);

			return fileEntryDisplayContextHelper.isDownloadActionAvailable();
		}
		catch (PortalException pe) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private final FileEntry _fileEntry;

}