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

import com.liferay.document.library.web.display.context.logic.UIItemsBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class OpenInMSOfficeFileEntryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public OpenInMSOfficeFileEntryPortletConfigurationIcon(
		PortletRequest portletRequest, FileEntry fileEntry,
		FileVersion fileVersion) {

		super(portletRequest);

		_fileEntry = fileEntry;
		_fileVersion = fileVersion;
	}

	@Override
	public String getMessage() {
		return "open-in-ms-office";
	}

	@Override
	public String getOnClick() {
		StringBundler sb = new StringBundler(4);

		try {
			String webDavURL = DLUtil.getWebDavURL(
				themeDisplay, _fileEntry.getFolder(), _fileEntry,
				PropsValues.
					DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			sb.append(portletDisplay.getNamespace());

			sb.append("openDocument('");
			sb.append(webDavURL);
			sb.append("');");
		}
		catch (PortalException pe) {
		}

		return sb.toString();
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow() {
		try {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				portletRequest);

			UIItemsBuilder uiItemsBuilder = new UIItemsBuilder(
				request, _fileVersion);

			return uiItemsBuilder.isOpenInMsOfficeActionAvailable();
		}
		catch (PortalException pe) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	private final FileEntry _fileEntry;
	private final FileVersion _fileVersion;

}