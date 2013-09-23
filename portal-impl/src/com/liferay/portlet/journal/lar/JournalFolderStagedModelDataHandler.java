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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;

import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class JournalFolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<JournalFolder> {

	public static final String[] CLASS_NAMES = {JournalFolder.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		JournalFolder folder =
			JournalFolderLocalServiceUtil.fetchJournalFolderByUuidAndGroupId(
				uuid, groupId);

		if (folder != null) {
			JournalFolderLocalServiceUtil.deleteFolder(folder);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(JournalFolder folder) {
		return folder.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		if (folder.getParentFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

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
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		if (folder.getParentFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			String path = ExportImportPathUtil.getModelPath(
				portletDataContext, JournalFolder.class.getName(),
				folder.getParentFolderId());

			JournalFolder parentFolder =
				(JournalFolder)portletDataContext.getZipEntryAsObject(path);

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, parentFolder);
		}

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folder);

		JournalFolder importedFolder = null;

		long groupId = portletDataContext.getScopeGroupId();

		if (portletDataContext.isDataStrategyMirror()) {
			JournalFolder existingFolder =
				JournalFolderLocalServiceUtil.
					fetchJournalFolderByUuidAndGroupId(
						folder.getUuid(), groupId);

			if (existingFolder == null) {
				serviceContext.setUuid(folder.getUuid());

				importedFolder = JournalFolderLocalServiceUtil.addFolder(
					userId, groupId, parentFolderId, folder.getName(),
					folder.getDescription(), serviceContext);
			}
			else {
				importedFolder = JournalFolderLocalServiceUtil.updateFolder(
					userId, existingFolder.getFolderId(), parentFolderId,
					folder.getName(), folder.getDescription(), false,
					serviceContext);
			}
		}
		else {
			importedFolder = JournalFolderLocalServiceUtil.addFolder(
				userId, groupId, parentFolderId, folder.getName(),
				folder.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(folder, importedFolder);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		JournalFolder existingFolder =
			JournalFolderLocalServiceUtil.fetchJournalFolderByUuidAndGroupId(
				folder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) || !existingFolder.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingFolder.getTrashHandler();

		if (trashHandler.isRestorable(existingFolder.getFolderId())) {
			trashHandler.restoreTrashEntry(
				userId, existingFolder.getFolderId());
		}
	}

}