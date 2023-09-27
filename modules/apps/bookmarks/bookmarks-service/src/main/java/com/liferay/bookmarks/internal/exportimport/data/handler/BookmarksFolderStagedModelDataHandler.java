/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.exportimport.data.handler;

import com.liferay.bookmarks.constants.BookmarksFolderConstants;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 * @author Daniel Kocsis
 */
@Component(service = StagedModelDataHandler.class)
public class BookmarksFolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<BookmarksFolder> {

	public static final String[] CLASS_NAMES = {
		BookmarksFolder.class.getName()
	};

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

		if ((existingFolder == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedFolder = _stagedModelRepository.addStagedModel(
				portletDataContext, importedFolder);
		}
		else {
			importedFolder.setMvccVersion(existingFolder.getMvccVersion());
			importedFolder.setFolderId(existingFolder.getFolderId());

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
		target = "(model.class.name=com.liferay.bookmarks.model.BookmarksFolder)"
	)
	private StagedModelRepository<BookmarksFolder> _stagedModelRepository;

}