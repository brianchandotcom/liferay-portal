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

package com.liferay.document.library.web.internal.portlet.configuration.icon;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.internal.display.context.logic.FileEntryDisplayContextHelper;
import com.liferay.document.library.web.internal.portlet.action.ActionUtil;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"path=/document_library/view_file_entry"
	},
	service = PortletConfigurationIcon.class
)
public class DeleteFileEntryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		String key = "delete";

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			FileEntry fileEntry = ActionUtil.getFileEntry(portletRequest);

			if (isTrashEnabled(
					themeDisplay.getScopeGroupId(),
					fileEntry.getRepositoryId())) {

				key = "move-to-the-recycle-bin";
			}

			return LanguageUtil.get(
				getResourceBundle(getLocale(portletRequest)), key);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/edit_file_entry");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		FileEntry fileEntry = null;

		try {
			fileEntry = ActionUtil.getFileEntry(portletRequest);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}

		if (isTrashEnabled(
				themeDisplay.getScopeGroupId(), fileEntry.getRepositoryId())) {

			portletURL.setParameter(Constants.CMD, Constants.MOVE_TO_TRASH);
		}
		else {
			portletURL.setParameter(Constants.CMD, Constants.DELETE);
		}

		PortletURL redirectURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			PortletRequest.RENDER_PHASE);

		long folderId = fileEntry.getFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			redirectURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}
		else {
			redirectURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}

		redirectURL.setParameter("folderId", String.valueOf(folderId));

		portletURL.setParameter("redirect", redirectURL.toString());

		portletURL.setParameter(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

		return portletURL.toString();
	}

	@Override
	public double getWeight() {
		return 100;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			FileEntry fileEntry = ActionUtil.getFileEntry(portletRequest);

			FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
				new FileEntryDisplayContextHelper(
					themeDisplay.getPermissionChecker(), fileEntry);

			return fileEntryDisplayContextHelper.isFileEntryDeletable();
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	public boolean isToolTip() {
		return false;
	}

	protected boolean isTrashEnabled(long groupId, long repositoryId) {
		try {
			return DLTrashUtil.isTrashEnabled(groupId, repositoryId);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

}