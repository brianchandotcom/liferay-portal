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

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.nio.charset.CharsetEncoderUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.nio.charset.CharsetEncoder;

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

			if (resourceID.equals("/document_library/download_folder")) {
				downloadFolder(resourceRequest, resourceResponse);
			}
			else {
				downloadFileEntries(resourceRequest, resourceResponse);
			}

			return true;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected void downloadFileEntries(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(resourceRequest, "folderId");

		File file = null;
		InputStream inputStream = null;

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
			else if ((fileEntries.size() == 1) && fileShortcuts.isEmpty() &&
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
				String zipFileName = getZipFileName(folderId, themeDisplay);

				ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

				for (FileEntry fileEntry : fileEntries) {
					zipFileEntry(fileEntry, StringPool.SLASH, zipWriter);
				}

				for (FileShortcut fileShortcut : fileShortcuts) {
					FileEntry fileEntry = _dlAppService.getFileEntry(
						fileShortcut.getToFileEntryId());

					zipFileEntry(fileEntry, StringPool.SLASH, zipWriter);
				}

				for (Folder folder : folders) {
					zipFolder(
						folder.getRepositoryId(), folder.getFolderId(),
						StringPool.SLASH.concat(folder.getName()), zipWriter);
				}

				file = zipWriter.getFile();

				inputStream = new FileInputStream(file);

				PortletResponseUtil.sendFile(
					resourceRequest, resourceResponse, zipFileName, inputStream,
					ContentTypes.APPLICATION_ZIP);
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			if (file != null) {
				file.delete();
			}
		}
	}

	protected void downloadFolder(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long repositoryId = ParamUtil.getLong(resourceRequest, "repositoryId");
		long folderId = ParamUtil.getLong(resourceRequest, "folderId");

		File file = null;
		InputStream inputStream = null;

		try {
			String zipFileName = getZipFileName(folderId, themeDisplay);

			ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

			zipFolder(repositoryId, folderId, StringPool.SLASH, zipWriter);

			file = zipWriter.getFile();

			inputStream = new FileInputStream(file);

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse, zipFileName, inputStream,
				ContentTypes.APPLICATION_ZIP);
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			if (file != null) {
				file.delete();
			}
		}
	}

	protected String getZipFileName(long folderId, ThemeDisplay themeDisplay)
		throws PortalException {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = _dlAppService.getFolder(folderId);

			return folder.getName() + ".zip";
		}
		else {
			return themeDisplay.getScopeGroupName() + ".zip";
		}
	}

	@Reference(unbind = "-")
	protected void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	protected void zipFileEntry(
			FileEntry fileEntry, String path, ZipWriter zipWriter)
		throws Exception {

		String fileName = _sanitizeName(fileEntry.getFileName());

		zipWriter.addEntry(
			path + StringPool.SLASH + fileName, fileEntry.getContentStream());
	}

	protected void zipFolder(
			long repositoryId, long folderId, String path, ZipWriter zipWriter)
		throws Exception {

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			_dlAppService.getFoldersAndFileEntriesAndFileShortcuts(
				repositoryId, folderId, WorkflowConstants.STATUS_APPROVED,
				false, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Object entry : foldersAndFileEntriesAndFileShortcuts) {
			if (entry instanceof Folder) {
				Folder folder = (Folder)entry;

				String folderName = _sanitizeName(folder.getName());

				zipFolder(
					folder.getRepositoryId(), folder.getFolderId(),
					path.concat(StringPool.SLASH).concat(folderName),
					zipWriter);
			}
			else if (entry instanceof FileEntry) {
				zipFileEntry((FileEntry)entry, path, zipWriter);
			}
			else if (entry instanceof FileShortcut) {
				FileShortcut fileShortcut = (FileShortcut)entry;

				FileEntry fileEntry = _dlAppService.getFileEntry(
					fileShortcut.getToFileEntryId());

				zipFileEntry(fileEntry, path, zipWriter);
			}
		}
	}

	private String _sanitizeName(String name) {
		CharsetEncoder charsetEncoder = CharsetEncoderUtil.getCharsetEncoder(
			"US-ASCII");

		if (!charsetEncoder.canEncode(name)) {
			name = HtmlUtil.escapeURL(name);
		}

		return name;
	}

	private DLAppService _dlAppService;

}