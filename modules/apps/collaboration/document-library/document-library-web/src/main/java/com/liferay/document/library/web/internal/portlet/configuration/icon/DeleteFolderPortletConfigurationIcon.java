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
import com.liferay.document.library.web.internal.portlet.action.ActionUtil;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

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
		"path=/document_library/view_folder"
	},
	service = PortletConfigurationIcon.class
)
public class DeleteFolderPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Folder folder = null;

		try {
			folder = ActionUtil.getFolder(portletRequest);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}

		String key = "delete";

		if (isTrashEnabled(
				themeDisplay.getScopeGroupId(), folder.getRepositoryId())) {

			key = "move-to-the-recycle-bin";
		}

		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), key);
	}

	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			PortletRequest.ACTION_PHASE);

		Folder folder = null;

		try {
			folder = ActionUtil.getFolder(portletRequest);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}

		if (folder.isMountPoint()) {
			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/document_library/edit_repository");
		}
		else {
			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/document_library/edit_folder");
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (folder.isMountPoint() ||
			!isTrashEnabled(
				themeDisplay.getScopeGroupId(), folder.getRepositoryId())) {

			portletURL.setParameter(Constants.CMD, Constants.DELETE);
		}
		else {
			portletURL.setParameter(Constants.CMD, Constants.MOVE_TO_TRASH);
		}

		PortletURL redirectURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			PortletRequest.RENDER_PHASE);

		long parentFolderId = folder.getParentFolderId();

		if (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			redirectURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}
		else {
			redirectURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}

		redirectURL.setParameter("folderId", String.valueOf(parentFolderId));

		portletURL.setParameter("redirect", redirectURL.toString());
		portletURL.setParameter(
			"folderId", String.valueOf(folder.getFolderId()));

		return portletURL.toString();
	}

	@Override
	public double getWeight() {
		return 100;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			Folder folder = ActionUtil.getFolder(portletRequest);

			return DLFolderPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folder.getFolderId(),
				ActionKeys.DELETE);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Override
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