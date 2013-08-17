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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.exception.FCKException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Ivica Cardic
 */
public class DocumentCommandReceiver extends BaseCommandReceiver {

	@Override
	protected String createFolder(CommandArgument commandArgument) {
		try {
			Group group = commandArgument.getCurrentGroup();

			Folder folder = _getFolder(
				group.getGroupId(),
				StringPool.SLASH + commandArgument.getCurrentFolder());

			long repositoryId = folder.getRepositoryId();
			long parentFolderId = folder.getFolderId();
			String name = commandArgument.getNewFolder();
			String description = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DLAppServiceUtil.addFolder(
				repositoryId, parentFolderId, name, description,
				serviceContext);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	@Override
	protected String fileUpload(
		CommandArgument commandArgument, String fileName,
		InputStream inputStream, String contentType, long size) {

		try {
			Group group = commandArgument.getCurrentGroup();

			Folder folder = _getFolder(
				group.getGroupId(), commandArgument.getCurrentFolder());

			long repositoryId = folder.getRepositoryId();
			long folderId = folder.getFolderId();
			String title = fileName;
			String description = StringPool.BLANK;
			String changeLog = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DLAppServiceUtil.addFileEntry(
				repositoryId, folderId, title, contentType, title, description,
				changeLog, inputStream, size, serviceContext);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	protected Element getFileElement(
			Element fileElement, ThemeDisplay themeDisplay, FileEntry fileEntry)
		throws Exception {

		String name = fileEntry.getTitle();

		String extension = fileEntry.getExtension();

		if (Validator.isNotNull(extension)) {
			String periodAndExtension = StringPool.PERIOD.concat(extension);

			if (!name.endsWith(periodAndExtension)) {
				name = name.concat(periodAndExtension);
			}
		}

		fileElement.setAttribute("name", name);

		String description = fileEntry.getDescription();

		fileElement.setAttribute(
			"desc", Validator.isNotNull(description) ? description : name);

		fileElement.setAttribute("size", getSize(fileEntry.getSize()));

		String url = DLUtil.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			StringPool.BLANK, false, false);

		fileElement.setAttribute("url", url);

		return fileElement;
	}

	protected List<Element> getFileElements(
			Document document, ThemeDisplay themeDisplay, Folder folder)
		throws Exception {

		List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(
			folder.getRepositoryId(), folder.getFolderId());

		List<Element> fileElements = new ArrayList<Element>(fileEntries.size());

		for (FileEntry fileEntry : fileEntries) {
			Element fileElement = document.createElement("File");

			fileElement = getFileElement(fileElement, themeDisplay, fileEntry);

			fileElements.add(fileElement);
		}

		return fileElements;
	}

	@Override
	protected void getFolders(
		CommandArgument commandArgument, Document document, Node rootNode) {

		try {
			_getFolders(commandArgument, document, rootNode);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	@Override
	protected void getFoldersAndFiles(
		CommandArgument commandArgument, Document document, Node rootNode) {

		try {
			_getFolders(commandArgument, document, rootNode);
			_getFiles(commandArgument, document, rootNode);
		}
		catch (PrincipalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	@Override
	protected boolean isStagedData(Group group) {
		return group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY);
	}

	private void _getFiles(
			CommandArgument commandArgument, Document document, Node rootNode)
		throws Exception {

		Element filesElement = document.createElement("Files");

		rootNode.appendChild(filesElement);

		if (Validator.isNull(commandArgument.getCurrentGroupName())) {
			return;
		}

		Group group = commandArgument.getCurrentGroup();

		Folder folder = _getFolder(
			group.getGroupId(), commandArgument.getCurrentFolder());

		List<Element> fileElements = getFileElements(
			document, commandArgument.getThemeDisplay(), folder);

		for (Element fileElement : fileElements) {
			filesElement.appendChild(fileElement);
		}
	}

	private Folder _getFolder(long groupId, String folderName)
		throws Exception {

		DLFolder dlFolder = new DLFolderImpl();

		dlFolder.setFolderId(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		dlFolder.setGroupId(groupId);
		dlFolder.setRepositoryId(groupId);

		Folder folder = new LiferayFolder(dlFolder);

		if (folderName.equals(StringPool.SLASH)) {
			return folder;
		}

		StringTokenizer st = new StringTokenizer(folderName, StringPool.SLASH);

		while (st.hasMoreTokens()) {
			String curFolderName = st.nextToken();

			List<Folder> folders = DLAppServiceUtil.getFolders(
				folder.getRepositoryId(), folder.getFolderId());

			for (Folder curFolder : folders) {
				if (curFolder.getName().equals(curFolderName)) {
					folder = curFolder;

					break;
				}
			}
		}

		return folder;
	}

	private void _getFolders(
			CommandArgument commandArgument, Document document, Node rootNode)
		throws Exception {

		Element foldersElement = document.createElement("Folders");

		rootNode.appendChild(foldersElement);

		if (commandArgument.getCurrentFolder().equals(StringPool.SLASH)) {
			getRootFolders(commandArgument, document, foldersElement);
		}
		else {
			Group group = commandArgument.getCurrentGroup();

			Folder folder = _getFolder(
				group.getGroupId(), commandArgument.getCurrentFolder());

			List<Folder> folders = DLAppServiceUtil.getFolders(
				folder.getRepositoryId(), folder.getFolderId());

			for (Folder curFolder : folders) {
				Element folderElement = document.createElement("Folder");

				foldersElement.appendChild(folderElement);

				folderElement.setAttribute("name", curFolder.getName());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DocumentCommandReceiver.class);

}