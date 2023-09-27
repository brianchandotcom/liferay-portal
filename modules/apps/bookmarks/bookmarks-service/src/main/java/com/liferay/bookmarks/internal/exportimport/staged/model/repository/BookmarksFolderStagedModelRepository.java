/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.exportimport.staged.model.repository;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 * @author Máté Thurzó
 */
@Component(
	property = "model.class.name=com.liferay.bookmarks.model.BookmarksFolder",
	service = StagedModelRepository.class
)
public class BookmarksFolderStagedModelRepository
	implements StagedModelRepository<BookmarksFolder> {

	@Override
	public BookmarksFolder addStagedModel(
			PortletDataContext portletDataContext,
			BookmarksFolder bookmarksFolder)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			bookmarksFolder.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			bookmarksFolder);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(bookmarksFolder.getUuid());
		}

		return _bookmarksFolderLocalService.addFolder(
			userId, bookmarksFolder.getParentFolderId(),
			bookmarksFolder.getName(), bookmarksFolder.getDescription(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(BookmarksFolder bookmarksFolder)
		throws PortalException {

		_bookmarksFolderLocalService.deleteFolder(bookmarksFolder);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		BookmarksFolder bookmarksFolder = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (bookmarksFolder != null) {
			deleteStagedModel(bookmarksFolder);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_bookmarksFolderLocalService.deleteFolders(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public BookmarksFolder fetchMissingReference(String uuid, long groupId) {
		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public BookmarksFolder fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _bookmarksFolderLocalService.
			fetchBookmarksFolderByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<BookmarksFolder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _bookmarksFolderLocalService.
			getBookmarksFoldersByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<BookmarksFolder>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _bookmarksFolderLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public BookmarksFolder getStagedModel(long folderId)
		throws PortalException {

		return _bookmarksFolderLocalService.getBookmarksFolder(folderId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			BookmarksFolder bookmarksFolder)
		throws PortletDataException {

		BookmarksFolder existingFolder = fetchStagedModelByUuidAndGroupId(
			bookmarksFolder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) ||
			!_stagedModelRepositoryHelper.isStagedModelInTrash(
				existingFolder)) {

			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			BookmarksFolder.class.getName());

		try {
			if (trashHandler.isRestorable(existingFolder.getFolderId())) {
				trashHandler.restoreTrashEntry(
					portletDataContext.getUserId(bookmarksFolder.getUserUuid()),
					existingFolder.getFolderId());
			}
		}
		catch (PortalException portalException) {
			throw new PortletDataException(portalException);
		}
	}

	@Override
	public BookmarksFolder saveStagedModel(BookmarksFolder bookmarksFolder) {
		return _bookmarksFolderLocalService.updateBookmarksFolder(
			bookmarksFolder);
	}

	@Override
	public BookmarksFolder updateStagedModel(
			PortletDataContext portletDataContext,
			BookmarksFolder bookmarksFolder)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			bookmarksFolder.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			bookmarksFolder);

		return _bookmarksFolderLocalService.updateFolder(
			userId, bookmarksFolder.getFolderId(),
			bookmarksFolder.getParentFolderId(), bookmarksFolder.getName(),
			bookmarksFolder.getDescription(), serviceContext);
	}

	@Reference
	private BookmarksFolderLocalService _bookmarksFolderLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}