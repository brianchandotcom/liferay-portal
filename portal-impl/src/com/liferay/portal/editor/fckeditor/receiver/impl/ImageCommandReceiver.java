/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.imagegallery.service.IGFolderServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageServiceUtil;

import java.io.File;

import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Ivica Cardic
 */
public class ImageCommandReceiver extends BaseCommandReceiver {

	@Override
	protected String createFolder(CommandArgument commandArgument) {
		try {
			Group group = commandArgument.getCurrentGroup();

			IGFolder igFolder = _getFolder(
				group.getGroupId(),
				StringPool.SLASH + commandArgument.getCurrentFolder());

			long parentFolderId = igFolder.getFolderId();
			String name = commandArgument.getNewFolder();
			String description = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setPlid(commandArgument.getPlid());
			serviceContext.setScopeGroupId(group.getGroupId());

			IGFolderServiceUtil.addFolder(
				parentFolderId, name, description, serviceContext);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	@Override
	protected String fileUpload(
		CommandArgument commandArgument, String fileName, File file,
		String extension) {

		try {
			Group group = commandArgument.getCurrentGroup();

			long groupId = group.getGroupId();

			IGFolder igFolder = _getFolder(
				groupId, commandArgument.getCurrentFolder());

			long folderId = igFolder.getFolderId();
			String name = fileName;
			String description = StringPool.BLANK;
			String contentType = extension.toLowerCase();

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			IGImageServiceUtil.addImage(
				groupId, folderId, name, description, file, contentType,
				serviceContext);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
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
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	@Override
	protected boolean isStagedData(Group group) {
		return group.isStagedPortlet(PortletKeys.IMAGE_GALLERY_DISPLAY);
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

		IGFolder igFolder = _getFolder(
			group.getGroupId(), commandArgument.getCurrentFolder());

		List<IGImage> images = IGImageServiceUtil.getImages(
			igFolder.getGroupId(), igFolder.getFolderId());

		for (IGImage image : images) {
			long largeImageId = image.getLargeImageId();

			Image portalImage = ImageLocalServiceUtil.getImageOrDefault(
				largeImageId);

			Element fileElement = document.createElement("File");

			filesElement.appendChild(fileElement);

			fileElement.setAttribute("name", image.getNameWithExtension());
			fileElement.setAttribute("desc", image.getNameWithExtension());
			fileElement.setAttribute("size", getSize(portalImage.getSize()));

			StringBundler url = new StringBundler(7);

			ThemeDisplay themeDisplay = commandArgument.getThemeDisplay();

			url.append(themeDisplay.getPathImage());
			url.append("/image_gallery?uuid=");
			url.append(image.getUuid());
			url.append("&groupId=");
			url.append(igFolder.getGroupId());
			url.append("&t=");
			url.append(ImageServletTokenUtil.getToken(largeImageId));

			fileElement.setAttribute("url", url.toString());
		}
	}

	private IGFolder _getFolder(long groupId, String folderName)
		throws Exception {

		IGFolder igFolder = new IGFolderImpl();

		igFolder.setFolderId(IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		igFolder.setGroupId(groupId);

		if (folderName.equals(StringPool.SLASH)) {
			return igFolder;
		}

		StringTokenizer st = new StringTokenizer(folderName, StringPool.SLASH);

		while (st.hasMoreTokens()) {
			String curFolderName = st.nextToken();

			List<IGFolder> igFolders = IGFolderServiceUtil.getFolders(
				groupId, igFolder.getFolderId());

			for (IGFolder curIGFolder : igFolders) {
				if (curIGFolder.getName().equals(curFolderName)) {
					igFolder = curIGFolder;

					break;
				}
			}
		}

		return igFolder;
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

			IGFolder igFolder = _getFolder(
				group.getGroupId(), commandArgument.getCurrentFolder());

			List<IGFolder> igFolders = IGFolderServiceUtil.getFolders(
				group.getGroupId(), igFolder.getFolderId());

			for (IGFolder curIGFolder : igFolders) {
				Element folderElement = document.createElement("Folder");

				foldersElement.appendChild(folderElement);

				folderElement.setAttribute("name", curIGFolder.getName());
			}
		}
	}

}