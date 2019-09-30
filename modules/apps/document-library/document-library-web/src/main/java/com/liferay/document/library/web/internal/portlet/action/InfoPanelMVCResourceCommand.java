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

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/info_panel"
	},
	service = MVCResourceCommand.class
)
public class InfoPanelMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		if (ParamUtil.getBoolean(resourceRequest, "selectAll")) {
			BulkSelection<FileEntry> fileEntryBulkSelection =
				_fileEntryBulkSelectionFactory.create(
					resourceRequest.getParameterMap());

			BulkSelection<FileShortcut> fileShortcutBulkSelection =
				_fileShortcutBulkSelectionFactory.create(
					resourceRequest.getParameterMap());

			BulkSelection<Folder> folderBulkSelection =
				_folderBulkSelectionFactory.create(
					resourceRequest.getParameterMap());

			resourceRequest.setAttribute(
				DLWebKeys.DOCUMENT_LIBRARY_SELECT_ALL_COUNT,
				fileEntryBulkSelection.getSize() +
					fileShortcutBulkSelection.getSize() +
						folderBulkSelection.getSize());

			include(
				resourceRequest, resourceResponse,
				"/document_library/info_panel_select_all.jsp");

			return;
		}

		resourceRequest.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_ENTRIES,
			ActionUtil.getFileEntries(resourceRequest));
		resourceRequest.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUTS,
			ActionUtil.getFileShortcuts(resourceRequest));
		resourceRequest.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FOLDERS,
			ActionUtil.getFolders(resourceRequest));
		resourceRequest.setAttribute(
			DLWebKeys.DOCUMENT_LIBRARY_TRASH_UTIL, _dlTrashUtil);

		include(
			resourceRequest, resourceResponse,
			"/document_library/info_panel.jsp");
	}

	@Reference
	private DLTrashUtil _dlTrashUtil;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileEntry)"
	)
	private BulkSelectionFactory<FileEntry> _fileEntryBulkSelectionFactory;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileShortcut)"
	)
	private BulkSelectionFactory<FileShortcut>
		_fileShortcutBulkSelectionFactory;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFolder)"
	)
	private BulkSelectionFactory<Folder> _folderBulkSelectionFactory;

}