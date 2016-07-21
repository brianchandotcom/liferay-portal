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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.internal.portlet.action.ActionUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

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
		"path=/document_library/view", "path=/document_library/view_folder",
		"path=-"
	},
	service = PortletConfigurationIcon.class
)
public class EditFolderPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "edit");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			PortletRequest.RENDER_PHASE);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		Folder folder = null;

		try {
			folder = ActionUtil.getFolder(portletRequest);
		}
		catch (Exception e) {
			return null;
		}

		if (folder == null) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/edit_folder");
			portletURL.setParameter(
				"folderId",
				String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));
			portletURL.setParameter(
				"repositoryId", String.valueOf(themeDisplay.getScopeGroupId()));
			portletURL.setParameter("rootFolder", Boolean.TRUE.toString());
		}
		else {
			if (folder.isMountPoint()) {
				portletURL.setParameter(
					"mvcRenderCommandName",
					"/document_library/edit_repository");
			}
			else {
				portletURL.setParameter(
					"mvcRenderCommandName", "/document_library/edit_folder");
			}

			portletURL.setParameter(
				"folderId", String.valueOf(folder.getFolderId()));
			portletURL.setParameter(
				"repositoryId", String.valueOf(folder.getRepositoryId()));
		}

		return portletURL.toString();
	}

	@Override
	public double getWeight() {
		return 106;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			Folder folder = ActionUtil.getFolder(portletRequest);

			if (folder == null) {
				if (!WorkflowEngineManagerUtil.isDeployed() ||
					(WorkflowHandlerRegistryUtil.getWorkflowHandler(
						DLFileEntry.class.getName()) == null)) {

					return false;
				}
			}
			else {
				folderId = folder.getFolderId();
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return DLFolderPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), folderId, ActionKeys.UPDATE);
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}