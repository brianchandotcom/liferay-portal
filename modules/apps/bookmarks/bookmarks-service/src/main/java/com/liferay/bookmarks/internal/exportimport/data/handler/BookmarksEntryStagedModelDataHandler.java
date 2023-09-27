/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.exportimport.data.handler;

import com.liferay.bookmarks.constants.BookmarksFolderConstants;
import com.liferay.bookmarks.internal.exportimport.staged.model.repository.BookmarksEntryStagedModelRepositoryUtil;
import com.liferay.bookmarks.model.BookmarksEntry;
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
public class BookmarksEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<BookmarksEntry> {

	public static final String[] CLASS_NAMES = {BookmarksEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(BookmarksEntry entry) {
		return entry.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, BookmarksEntry entry)
		throws Exception {

		if (entry.getFolderId() !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, entry, entry.getFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element entryElement = portletDataContext.getExportDataElement(entry);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(entry), entry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long entryId)
		throws Exception {

		BookmarksEntry existingEntry = fetchMissingReference(uuid, groupId);

		if (existingEntry == null) {
			return;
		}

		Map<Long, Long> entryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksEntry.class);

		entryIds.put(entryId, existingEntry.getEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, BookmarksEntry entry)
		throws Exception {

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long folderId = MapUtil.getLong(
			folderIds, entry.getFolderId(), entry.getFolderId());

		BookmarksEntry importedEntry = (BookmarksEntry)entry.clone();

		importedEntry.setGroupId(portletDataContext.getScopeGroupId());
		importedEntry.setFolderId(folderId);

		BookmarksEntry existingEntry =
			_bookmarksEntryStagedModelRepository.
				fetchStagedModelByUuidAndGroupId(
					entry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedEntry = _bookmarksEntryStagedModelRepository.addStagedModel(
				portletDataContext, importedEntry);
		}
		else {
			importedEntry =
				BookmarksEntryStagedModelRepositoryUtil.updateStagedModel(
					portletDataContext, importedEntry,
					existingEntry.getEntryId());
		}

		portletDataContext.importClassedModel(entry, importedEntry);
	}

	@Override
	protected StagedModelRepository<BookmarksEntry> getStagedModelRepository() {
		return _bookmarksEntryStagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.bookmarks.model.BookmarksEntry)"
	)
	private StagedModelRepository<BookmarksEntry>
		_bookmarksEntryStagedModelRepository;

}