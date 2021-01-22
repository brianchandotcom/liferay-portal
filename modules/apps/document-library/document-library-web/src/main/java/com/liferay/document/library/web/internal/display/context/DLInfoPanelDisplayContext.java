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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia Garcia
 */
public class DLInfoPanelDisplayContext {

	public DLInfoPanelDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public List<FileEntry> getFileEntryList() {
		if (_fileEntries != null) {
			return _fileEntries;
		}

		_fileEntries = (List<FileEntry>)_httpServletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_ENTRIES);

		return _fileEntries;
	}

	public List<FileShortcut> getFileShortcutList() {
		if (_fileShortcuts != null) {
			return _fileShortcuts;
		}

		_fileShortcuts = (List<FileShortcut>)_httpServletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUTS);

		return _fileShortcuts;
	}

	public List<Folder> getFolderList() throws PortalException {
		if (_folders != null) {
			return _folders;
		}

		_folders = (List<Folder>)_httpServletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FOLDERS);

		if (ListUtil.isEmpty(_folders) &&
			ListUtil.isEmpty(getFileEntryList()) &&
			ListUtil.isEmpty(getFileShortcutList())) {

			long folderId = GetterUtil.getLong(
				(String)_httpServletRequest.getAttribute("view.jsp-folderId"),
				ParamUtil.getLong(_httpServletRequest, "folderId"));

			_folders = new ArrayList<>();

			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				_folders.add(DLAppLocalServiceUtil.getFolder(folderId));
			}
			else {
				_folders.add(null);
			}
		}

		return _folders;
	}

	private List<FileEntry> _fileEntries;
	private List<FileShortcut> _fileShortcuts;
	private List<Folder> _folders;
	private final HttpServletRequest _httpServletRequest;

}