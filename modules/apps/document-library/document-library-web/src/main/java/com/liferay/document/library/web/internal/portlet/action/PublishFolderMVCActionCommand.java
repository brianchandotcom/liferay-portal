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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.portlet.action.ExportImportChangesetMVCActionCommand;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/publish_folder"
	},
	service = MVCActionCommand.class
)
public class PublishFolderMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		final long folderId = ParamUtil.getLong(actionRequest, "folderId");

		Folder folder = _dlAppLocalService.getFolder(folderId);

		Changeset.Builder builder = Changeset.create();

		Changeset changeset = builder.addStagedModel(
			() -> _getFolder(folderId)
		).addMultipleStagedModel(
			() -> _getFoldersAndFileEntriesAndFileShortcuts(folder)
		).build();

		_exportImportChangesetMVCActionCommand.processPublishAction(
			actionRequest, actionResponse, changeset);
	}

	private Folder _getFolder(long folderId) {
		try {
			return _dlAppLocalService.getFolder(folderId);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get folder " + folderId, pe);
			}

			return null;
		}
	}

	private List<StagedModel> _getFoldersAndFileEntriesAndFileShortcuts(
		Folder folder) {

		if (folder == null) {
			return Collections.emptyList();
		}

		List<StagedModel> stagedModels = new ArrayList<>();

		try {
			List<Object> childObjects =
				_dlAppService.getFoldersAndFileEntriesAndFileShortcuts(
					folder.getGroupId(), folder.getFolderId(),
					WorkflowConstants.STATUS_ANY, false, -1, -1);

			for (Object childObject : childObjects) {
				if (childObject instanceof Folder) {
					Folder childFolder = (Folder)childObject;

					stagedModels.add(childFolder);

					stagedModels.addAll(
						_getFoldersAndFileEntriesAndFileShortcuts(childFolder));
				}
				else if (childObject instanceof FileEntry) {
					FileEntry fileEntry = (FileEntry)childObject;

					FileVersion fileVersion = fileEntry.getFileVersion();

					StagedModelDataHandler<FileEntry> stagedModelDataHandler =
						_getStagedModelDataHandler();

					if (ArrayUtil.contains(
							stagedModelDataHandler.getExportableStatuses(),
							fileVersion.getStatus())) {

						stagedModels.add((StagedModel)childObject);
					}
				}
				else {
					stagedModels.add((StagedModel)childObject);
				}
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get folders, file entries, and file shortcuts " +
						"for folder " + folder.getFolderId(),
					pe);
			}
		}

		return stagedModels;
	}

	private StagedModelDataHandler<FileEntry> _getStagedModelDataHandler() {
		return (StagedModelDataHandler<FileEntry>)
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				FileEntry.class.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PublishFolderMVCActionCommand.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private ExportImportChangesetMVCActionCommand
		_exportImportChangesetMVCActionCommand;

}