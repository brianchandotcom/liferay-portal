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

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.display.context.logic.FileEntryDisplayContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public class MoveFileEntryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public MoveFileEntryPortletConfigurationIcon(
		PortletRequest portletRequest, FileEntry fileEntry) {

		super(portletRequest);

		_fileEntry = fileEntry;
	}

	@Override
	public String getMessage() {
		return "move";
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/move_entry");

		PortletURL redirectURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			PortletRequest.RENDER_PHASE);

		redirectURL.setParameter(
			"mvcRenderCommandName", "/document_library/view");
		redirectURL.setParameter(
			"folderId", String.valueOf(_fileEntry.getFolderId()));

		portletURL.setParameter("redirect", redirectURL.toString());

		portletURL.setParameter(
			"rowIdsFileEntry", String.valueOf(_fileEntry.getFileEntryId()));

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		try {
			FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
				new FileEntryDisplayContextHelper(
					themeDisplay.getPermissionChecker(), _fileEntry);

			return fileEntryDisplayContextHelper.isMoveActionAvailable();
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