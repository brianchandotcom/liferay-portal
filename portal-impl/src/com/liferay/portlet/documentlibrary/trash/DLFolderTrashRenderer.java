/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.trash.BaseTrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alexander Chow
 */
public class DLFolderTrashRenderer extends BaseTrashRenderer {

	public static final String TYPE = "folder";

	public DLFolderTrashRenderer(Folder folder) {
		_folder = folder;
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		int foldersCount = 0;
		int fileEntriesAndFileShortcutsCount = 0;

		try {
			foldersCount = DLAppServiceUtil.getFoldersCount(
				_folder.getRepositoryId(), _folder.getFolderId());

			fileEntriesAndFileShortcutsCount =
				DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(
					_folder.getRepositoryId(), _folder.getFolderId(),
					WorkflowConstants.STATUS_APPROVED);
		}
		catch (Exception e) {
		}

		if ((foldersCount + fileEntriesAndFileShortcutsCount) > 0) {
			return themeDisplay.getPathThemeImages() +
				"/common/folder_full_document.png";
		}

		return themeDisplay.getPathThemeImages() + "/common/folder_empty.png";
	}

	public String getPortletId() {
		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				DLFileEntry.class.getName());

		return assetRendererFactory.getPortletId();
	}

	@Override
	public String getRestorePath(RenderRequest renderRequest) {
		DLFolder dlFolder = (DLFolder)_folder.getModel();

		if ((dlFolder != null) && dlFolder.isInTrashFolder()) {
			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FOLDER, _folder);

			return "/html/portlet/document_library/trash/folder_restore.jsp";
		}

		return null;
	}

	public String getSummary(Locale locale) {
		return HtmlUtil.stripHtml(_folder.getDescription());
	}

	public String getTitle(Locale locale) {
		return _folder.getName();
	}

	public String getType() {
		return TYPE;
	}

	public boolean hasDeletePermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return DLFolderPermission.contains(
			permissionChecker, _folder, ActionKeys.DELETE);
	}

	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return DLFolderPermission.contains(
			permissionChecker, _folder, ActionKeys.VIEW);
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		renderRequest.setAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER, _folder);

		return "/html/portlet/document_library/trash/folder.jsp";
	}

	@Override
	public String renderActions(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		renderRequest.setAttribute("view_entries.jsp-folder", _folder);

		return "/html/portlet/document_library/folder_action.jsp";
	}

	private Folder _folder;

}