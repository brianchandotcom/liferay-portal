/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;

import java.util.Map;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class BookmarksFolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<BookmarksFolder> {

	@Override
	public String getClassName() {
		return BookmarksFolder.class.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			BookmarksFolder folder)
		throws Exception {

		Element foldersElement = elements[0];

		if (folder.getParentFolderId() !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			exportStagedModel(
				portletDataContext, foldersElement, folder.getParentFolder());
		}

		Element folderElement = foldersElement.addElement("folder");

		portletDataContext.addClassedModel(
			folderElement, StagedModelPathUtil.getPath(folder), folder,
			BookmarksPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Element element,
			BookmarksFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		if ((parentFolderId !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String parentFolderPath = StagedModelPathUtil.getPath(
				portletDataContext, BookmarksFolder.class.getName(),
				parentFolderId);

			BookmarksFolder parentFolder =
				(BookmarksFolder)portletDataContext.getZipEntryAsObject(
					parentFolderPath);

			importStagedModel(portletDataContext, element, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderIds, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folder, BookmarksPortletDataHandler.NAMESPACE);

		BookmarksFolder importedFolder = null;

		if (portletDataContext.isDataStrategyMirror()) {
			BookmarksFolder existingFolder = BookmarksFolderUtil.fetchByUUID_G(
				folder.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFolder == null) {
				serviceContext.setUuid(folder.getUuid());

				importedFolder = BookmarksFolderLocalServiceUtil.addFolder(
					userId, parentFolderId, folder.getName(),
					folder.getDescription(), serviceContext);
			}
			else {
				importedFolder = BookmarksFolderLocalServiceUtil.updateFolder(
					userId, existingFolder.getFolderId(), parentFolderId,
					folder.getName(), folder.getDescription(), false,
					serviceContext);
			}
		}
		else {
			importedFolder = BookmarksFolderLocalServiceUtil.addFolder(
				userId, parentFolderId, folder.getName(),
				folder.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(
			folder, importedFolder, BookmarksPortletDataHandler.NAMESPACE);
	}

}