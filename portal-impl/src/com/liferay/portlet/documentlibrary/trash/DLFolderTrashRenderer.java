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

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.trash.BaseTrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.Locale;

/**
 * @author Alexander Chow
 */
public class DLFolderTrashRenderer extends BaseTrashRenderer {

	public static final String TYPE = "folder";

	public DLFolderTrashRenderer(Folder folder) {
		_folder = folder;
	}

	public String getClassName() {
		return DLFolder.class.getName();
	}

	public long getClassPK() {
		return _folder.getPrimaryKey();
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

	public String getSummary(Locale locale) {
		return HtmlUtil.stripHtml(_folder.getDescription());
	}

	public String getTitle(Locale locale) {
		return TrashUtil.getOriginalTitle(_folder.getName());
	}

	public String getType() {
		return TYPE;
	}

	private Folder _folder;

}