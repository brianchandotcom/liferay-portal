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
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.util.RepositoryUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Sergio González
 * @author Levente Hudák
 * @author Roberto Díaz
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/download_entry",
		"mvc.command.name=/document_library/download_folder"
	},
	service = MVCResourceCommand.class
)
public class DownloadEntriesMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			String resourceID = GetterUtil.getString(
				resourceRequest.getResourceID());

			boolean selectAll = ParamUtil.getBoolean(
				resourceRequest, "selectAll");

			if (selectAll ||
				resourceID.equals("/document_library/download_folder")) {

				_downloadFolder(resourceRequest, resourceResponse);
			}
			else {
				_downloadFileEntries(resourceRequest, resourceResponse);
			}

			return false;
		}
		catch (IOException | PortalException e) {
			throw new PortletException(e);
		}
	}

	private void _checkFolder(long folderId) throws PortalException {
		if (_isExternalRepositoryFolder(folderId)) {
			throw new InvalidRepositoryException(
				"Tried to download Folder " + folderId +
					" belonging to an external repository");
		}
	}

	private void _downloadFileEntries(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(resourceRequest, "folderId");

		File file = null;

		try {
			List<FileEntry> fileEntries = ActionUtil.getFileEntries(
				resourceRequest);

			List<FileShortcut> fileShortcuts = ActionUtil.getFileShortcuts(
				resourceRequest);

			List<Folder> folders = ActionUtil.getFolders(resourceRequest);

			if (fileEntries.isEmpty() && fileShortcuts.isEmpty() &&
				folders.isEmpty()) {

				return;
			}

			if ((fileEntries.size() == 1) && fileShortcuts.isEmpty() &&
				folders.isEmpty()) {

				FileEntry fileEntry = fileEntries.get(0);

				PortletResponseUtil.sendFile(
					resourceRequest, resourceResponse, fileEntry.getFileName(),
					fileEntry.getContentStream(), 0, fileEntry.getMimeType(),
					HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
			}
			else if ((fileShortcuts.size() == 1) && fileEntries.isEmpty() &&
					 folders.isEmpty()) {

				FileShortcut fileShortcut = fileShortcuts.get(0);

				FileEntry fileEntry = _dlAppService.getFileEntry(
					fileShortcut.getToFileEntryId());

				PortletResponseUtil.sendFile(
					resourceRequest, resourceResponse, fileEntry.getFileName(),
					fileEntry.getContentStream(), 0, fileEntry.getMimeType(),
					HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
			}
			else {
				String zipFileName = _getZipFileName(folderId, themeDisplay);

				ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

				for (FileEntry fileEntry : fileEntries) {
					_zipFileEntry(fileEntry, StringPool.SLASH, zipWriter);
				}

				for (FileShortcut fileShortcut : fileShortcuts) {
					FileEntry fileEntry = _dlAppService.getFileEntry(
						fileShortcut.getToFileEntryId());

					_zipFileEntry(fileEntry, StringPool.SLASH, zipWriter);
				}

				for (Folder folder : folders) {
					if (!_isExternalRepositoryFolder(folder)) {
						_zipFolder(
							folder.getRepositoryId(), folder.getFolderId(),
							StringPool.SLASH.concat(folder.getName()),
							zipWriter);
					}
				}

				file = zipWriter.getFile();

				try (InputStream inputStream = new FileInputStream(file)) {
					PortletResponseUtil.sendFile(
						resourceRequest, resourceResponse, zipFileName,
						inputStream, ContentTypes.APPLICATION_ZIP);
				}
			}
		}
		finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	private void _downloadFolder(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(resourceRequest, "folderId");

		_checkFolder(folderId);

		File file = null;

		try {
			String zipFileName = _getZipFileName(folderId, themeDisplay);

			ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

			long repositoryId = ParamUtil.getLong(
				resourceRequest, "repositoryId");

			_zipFolder(repositoryId, folderId, StringPool.SLASH, zipWriter);

			file = zipWriter.getFile();

			try (InputStream inputStream = new FileInputStream(file)) {
				PortletResponseUtil.sendFile(
					resourceRequest, resourceResponse, zipFileName, inputStream,
					ContentTypes.APPLICATION_ZIP);
			}
		}
		finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	private String _getZipFileName(long folderId, ThemeDisplay themeDisplay)
		throws PortalException {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = _dlAppService.getFolder(folderId);

			return folder.getName() + ".zip";
		}

		return themeDisplay.getScopeGroupName() + ".zip";
	}

	private boolean _isExternalRepositoryFolder(Folder folder) {
		if ((folder.isMountPoint() ||
			 (folder.getGroupId() != folder.getRepositoryId())) &&
			RepositoryUtil.isExternalRepository(folder.getRepositoryId())) {

			return true;
		}

		return false;
	}

	private boolean _isExternalRepositoryFolder(long folderId)
		throws PortalException {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return false;
		}

		if (_isExternalRepositoryFolder(_dlAppService.getFolder(folderId))) {
			return true;
		}

		return false;
	}

	private void _zipFileEntry(
			FileEntry fileEntry, String path, ZipWriter zipWriter)
		throws IOException, PortalException {

		zipWriter.addEntry(
			path + StringPool.SLASH + fileEntry.getFileName(),
			fileEntry.getContentStream());
	}

	private void _zipFolder(
			long repositoryId, long folderId, String path, ZipWriter zipWriter)
		throws IOException, PortalException {

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			_dlAppService.getFoldersAndFileEntriesAndFileShortcuts(
				repositoryId, folderId, WorkflowConstants.STATUS_APPROVED,
				false, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Object entry : foldersAndFileEntriesAndFileShortcuts) {
			if (entry instanceof Folder) {
				Folder folder = (Folder)entry;

				_zipFolder(
					folder.getRepositoryId(), folder.getFolderId(),
					path.concat(
						StringPool.SLASH
					).concat(
						folder.getName()
					),
					zipWriter);
			}
			else if (entry instanceof FileEntry) {
				_zipFileEntry((FileEntry)entry, path, zipWriter);
			}
			else if (entry instanceof FileShortcut) {
				FileShortcut fileShortcut = (FileShortcut)entry;

				FileEntry fileEntry = _dlAppService.getFileEntry(
					fileShortcut.getToFileEntryId());

				_zipFileEntry(fileEntry, path, zipWriter);
			}
		}
	}

	@Reference
	private DLAppService _dlAppService;

}