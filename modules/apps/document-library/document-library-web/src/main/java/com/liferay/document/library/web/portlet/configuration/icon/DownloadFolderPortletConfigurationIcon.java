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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;

/**
 * @author Roberto Díaz
 */
public class DownloadFolderPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public DownloadFolderPortletConfigurationIcon(
		PortletRequest portletRequest, Folder folder) {

		super(portletRequest);

		_folder = folder;
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
		ResourceURL portletURL =
			(ResourceURL)PortalUtil.getControlPanelPortletURL(
				portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
				PortletRequest.RESOURCE_PHASE);

		portletURL.setResourceID("/document_library/edit_folder");
		portletURL.setParameter(
			"folderId", String.valueOf(_folder.getFolderId()));
		portletURL.setParameter(
			"repositoryId", String.valueOf(_folder.getRepositoryId()));

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		try {
			return DLFolderPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), _folder.getFolderId(),
				ActionKeys.VIEW);
		}
		catch (PortalException e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private final Folder _folder;

}