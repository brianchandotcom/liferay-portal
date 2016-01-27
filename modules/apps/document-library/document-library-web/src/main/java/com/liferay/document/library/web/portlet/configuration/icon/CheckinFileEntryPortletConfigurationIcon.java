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
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

/**
 * @author Roberto Díaz
 */
public class CheckinFileEntryPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	public CheckinFileEntryPortletConfigurationIcon(
		ServletContext servletContext, String jspPath,
		PortletRequest portletRequest, FileEntry fileEntry) {

		super(servletContext, jspPath, portletRequest);

		_fileEntry = fileEntry;
	}

	@Override
	public String getMessage() {
		return "checkin";
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow() {
		try {
			FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
				new FileEntryDisplayContextHelper(
					themeDisplay.getPermissionChecker(), _fileEntry);

			return fileEntryDisplayContextHelper.isCheckinActionAvailable();
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private final FileEntry _fileEntry;

}