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

package com.liferay.bookmarks.lar;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class BookmarksFolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<BookmarksFolder> {

	public static final String[] CLASS_NAMES =
		{BookmarksFolder.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(BookmarksFolder folder) {
		return folder.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, BookmarksFolder folder)
		throws Exception {

		if (folder.getParentFolderId() !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, folder, folder.getParentFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element folderElement = portletDataContext.getExportDataElement(folder);

		portletDataContext.addClassedModel(
			folderElement, ExportImportPathUtil.getModelPath(folder), folder);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, BookmarksFolder folder)
		throws Exception {

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		BookmarksFolder importedFolder = (BookmarksFolder)folder.clone();

		importedFolder.setGroupId(portletDataContext.getScopeGroupId());
		importedFolder.setParentFolderId(parentFolderId);

		BookmarksFolder existingFolder =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				folder.getUuid(), portletDataContext.getScopeGroupId());

		if (existingFolder == null) {
			importedFolder = _stagedModelRepository.addStagedModel(
				portletDataContext, importedFolder);
		}
		else {
			folder.setFolderId(existingFolder.getFolderId());

			importedFolder = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedFolder);
		}

		portletDataContext.importClassedModel(folder, importedFolder);
	}

	@Override
	protected StagedModelRepository<BookmarksFolder>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target =
			"(model.class.name=com.liferay.bookmarks.model.BookmarksFolder)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<BookmarksFolder> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private volatile StagedModelRepository<BookmarksFolder> _stagedModelRepository;

}