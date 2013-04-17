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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutUtil;

import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class DLFileShortcutStagedModelDataHandler
	extends BaseStagedModelDataHandler<DLFileShortcut> {

	public static final String[] CLASS_NAMES = {DLFileShortcut.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DLFileShortcut fileShortcut)
		throws Exception {

		if (fileShortcut.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, fileShortcut);
		}

		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileShortcut.getToFileEntryId());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, fileEntry);

		String fileEntryUuid = fileEntry.getUuid();

		Element fileShortcutElement =
			portletDataContext.getExportDataStagedModelElement(fileShortcut);

		fileShortcutElement.addAttribute("file-entry-uuid", fileEntryUuid);

		portletDataContext.addClassedModel(
			fileShortcutElement,
			ExportImportPathUtil.getModelPath(fileShortcut), fileShortcut,
			DLPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DLFileShortcut fileShortcut)
		throws Exception {

		long userId = portletDataContext.getUserId(fileShortcut.getUserUuid());

		if (fileShortcut.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			String folderPath = ExportImportPathUtil.getModelPath(
				portletDataContext, Folder.class.getName(),
				fileShortcut.getFolderId());

			DLFolder dlFolder =
				(DLFolder)portletDataContext.getZipEntryAsObject(folderPath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, dlFolder);
		}

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		long folderId = MapUtil.getLong(
			folderIds, fileShortcut.getFolderId(), fileShortcut.getFolderId());

		String fileEntryPath = ExportImportPathUtil.getModelPath(
			portletDataContext, DLFileEntry.class.getName(),
			fileShortcut.getToFileEntryId());

		DLFileEntry dlFileEntry =
			(DLFileEntry)portletDataContext.getZipEntryAsObject(fileEntryPath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, dlFileEntry);

		long groupId = portletDataContext.getScopeGroupId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = FolderUtil.findByPrimaryKey(folderId);

			groupId = folder.getRepositoryId();
		}

		Element fileShortcutElement =
			portletDataContext.getImportDataStagedModelElement(fileShortcut);

		String fileEntryUuid = fileShortcutElement.attributeValue(
			"file-entry-uuid");

		FileEntry fileEntry = FileEntryUtil.fetchByUUID_R(
			fileEntryUuid, groupId);

		if (fileEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch file entry {uuid=" + fileEntryUuid +
						", groupId=" + groupId + "}");
			}

			return;
		}

		long fileEntryId = fileEntry.getFileEntryId();

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileShortcut, DLPortletDataHandler.NAMESPACE);

		DLFileShortcut importedFileShortcut = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DLFileShortcut existingFileShortcut =
				DLFileShortcutUtil.fetchByUUID_G(
					fileShortcut.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingFileShortcut == null) {
				serviceContext.setUuid(fileShortcut.getUuid());

				importedFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
					userId, groupId, folderId, fileEntryId, serviceContext);
			}
			else {
				importedFileShortcut = DLAppLocalServiceUtil.updateFileShortcut(
					userId, existingFileShortcut.getFileShortcutId(), folderId,
					fileEntryId, serviceContext);
			}
		}
		else {
			importedFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
				userId, groupId, folderId, fileEntryId, serviceContext);
		}

		portletDataContext.importClassedModel(
			fileShortcut, importedFileShortcut, DLPortletDataHandler.NAMESPACE);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFileShortcutStagedModelDataHandler.class);

}