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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class DLFileShortcutStagedModelDataHandler
	extends BaseStagedModelDataHandler<DLFileShortcut> {

	public static final String[] CLASS_NAMES = {DLFileShortcut.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DLFileShortcut dlFileShortcut = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (dlFileShortcut != null) {
			DLFileShortcutLocalServiceUtil.deleteFileShortcut(dlFileShortcut);
		}
	}

	@Override
	public DLFileShortcut fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<DLFileShortcut> fileShortcuts =
			DLFileShortcutLocalServiceUtil.getDLFileShortcutsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DLFileShortcut>());

		if (ListUtil.isEmpty(fileShortcuts)) {
			return null;
		}

		return fileShortcuts.get(0);
	}

	@Override
	public DLFileShortcut fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return DLFileShortcutLocalServiceUtil.
			fetchDLFileShortcutByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DLFileShortcut shortcut) {
		return shortcut.getToTitle();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DLFileShortcut fileShortcut)
		throws Exception {

		if (fileShortcut.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fileShortcut, fileShortcut.getFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			fileShortcut.getToFileEntryId());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, fileShortcut, fileEntry,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element fileShortcutElement = portletDataContext.getExportDataElement(
			fileShortcut);

		fileShortcutElement.addAttribute(
			"file-entry-uuid", fileEntry.getUuid());

		portletDataContext.addClassedModel(
			fileShortcutElement,
			ExportImportPathUtil.getModelPath(fileShortcut), fileShortcut);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DLFileShortcut fileShortcut)
		throws Exception {

		long userId = portletDataContext.getUserId(fileShortcut.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		long folderId = MapUtil.getLong(
			folderIds, fileShortcut.getFolderId(), fileShortcut.getFolderId());

		long groupId = portletDataContext.getScopeGroupId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = FolderUtil.findByPrimaryKey(folderId);

			groupId = folder.getRepositoryId();
		}

		Element fileShortcutElement =
			portletDataContext.getImportDataStagedModelElement(fileShortcut);

		String fileEntryUuid = fileShortcutElement.attributeValue(
			"file-entry-uuid");

		FileEntry importedFileEntry = FileEntryUtil.fetchByUUID_R(
			fileEntryUuid, groupId);

		if (importedFileEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch file entry {uuid=" + fileEntryUuid +
						", groupId=" + groupId + "}");
			}

			return;
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileShortcut);

		DLFileShortcut importedFileShortcut = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DLFileShortcut existingFileShortcut =
				fetchStagedModelByUuidAndGroupId(
					fileShortcut.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingFileShortcut == null) {
				serviceContext.setUuid(fileShortcut.getUuid());

				importedFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
					userId, groupId, folderId,
					importedFileEntry.getFileEntryId(), serviceContext);
			}
			else {
				importedFileShortcut = DLAppLocalServiceUtil.updateFileShortcut(
					userId, existingFileShortcut.getFileShortcutId(), folderId,
					importedFileEntry.getFileEntryId(), serviceContext);
			}
		}
		else {
			importedFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
				userId, groupId, folderId, importedFileEntry.getFileEntryId(),
				serviceContext);
		}

		portletDataContext.importClassedModel(
			fileShortcut, importedFileShortcut);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, DLFileShortcut fileShortcut)
		throws Exception {

		long userId = portletDataContext.getUserId(fileShortcut.getUserUuid());

		DLFileShortcut existingFileShortcut = fetchStagedModelByUuidAndGroupId(
			fileShortcut.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFileShortcut == null) ||
			!existingFileShortcut.isInTrash()) {

			return;
		}

		TrashHandler trashHandler = existingFileShortcut.getTrashHandler();

		if (trashHandler.isRestorable(
				existingFileShortcut.getFileShortcutId())) {

			trashHandler.restoreTrashEntry(
				userId, existingFileShortcut.getFileShortcutId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileShortcutStagedModelDataHandler.class);

}