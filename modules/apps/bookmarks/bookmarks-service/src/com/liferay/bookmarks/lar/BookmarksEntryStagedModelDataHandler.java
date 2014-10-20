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

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;

import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class BookmarksEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<BookmarksEntry> {

	public static final String[] CLASS_NAMES = {BookmarksEntry.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		BookmarksEntry entry = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (entry != null) {
			BookmarksEntryLocalServiceUtil.deleteEntry(entry);
		}
	}

	@Override
	public BookmarksEntry fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<BookmarksEntry> entries =
			BookmarksEntryLocalServiceUtil.
				getBookmarksEntriesByUuidAndCompanyId(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					new StagedModelModifiedDateComparator<BookmarksEntry>());

		if (ListUtil.isEmpty(entries)) {
			return null;
		}

		return entries.get(0);
	}

	@Override
	public BookmarksEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return BookmarksEntryLocalServiceUtil.
			fetchBookmarksEntryByUuidAndGroupId(uuid, groupId);
	}

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
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, BookmarksEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long folderId = MapUtil.getLong(
			folderIds, entry.getFolderId(), entry.getFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			entry);

		BookmarksEntry importedEntry = null;

		if (portletDataContext.isDataStrategyMirror()) {
			BookmarksEntry existingEntry = fetchStagedModelByUuidAndGroupId(
				entry.getUuid(), portletDataContext.getScopeGroupId());

			if (existingEntry == null) {
				serviceContext.setUuid(entry.getUuid());

				importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					entry.getName(), entry.getUrl(), entry.getDescription(),
					serviceContext);
			}
			else {
				importedEntry = BookmarksEntryLocalServiceUtil.updateEntry(
					userId, existingEntry.getEntryId(),
					portletDataContext.getScopeGroupId(), folderId,
					entry.getName(), entry.getUrl(), entry.getDescription(),
					serviceContext);
			}
		}
		else {
			importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
				userId, portletDataContext.getScopeGroupId(), folderId,
				entry.getName(), entry.getUrl(), entry.getDescription(),
				serviceContext);
		}

		portletDataContext.importClassedModel(entry, importedEntry);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, BookmarksEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		BookmarksEntry existingEntry = fetchStagedModelByUuidAndGroupId(
			entry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingEntry == null) || !existingEntry.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingEntry.getTrashHandler();

		if (trashHandler.isRestorable(existingEntry.getEntryId())) {
			trashHandler.restoreTrashEntry(userId, existingEntry.getEntryId());
		}
	}

}