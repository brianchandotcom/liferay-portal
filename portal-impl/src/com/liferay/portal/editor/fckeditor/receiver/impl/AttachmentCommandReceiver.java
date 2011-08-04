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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Julio Camarero
 */
public class AttachmentCommandReceiver extends BaseCommandReceiver {

	protected String createFolder(CommandArgument commandArgument) {
		return "0";
	}

	protected String fileUpload(
		CommandArgument commandArgument, String fileName, File file,
		String extension) {

		try {
			HttpServletRequest request =
				commandArgument.getHttpServletRequest();

			long resourcePK = ParamUtil.getLong(
				request, "wikiPageResourcePrimKey");

			WikiPage page = WikiPageLocalServiceUtil.getPage(resourcePK);

			String title = page.getTitle();

			long nodeId = page.getNodeId();

			List<ObjectValuePair<String, byte[]>> files =
				new ArrayList<ObjectValuePair<String, byte[]>>();

			if (file != null) {
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					ObjectValuePair<String, byte[]> ovp =
						new ObjectValuePair<String, byte[]>(fileName, bytes);

					files.add(ovp);
				}
			}

			WikiPageServiceUtil.addPageAttachments(nodeId, title, files);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	protected void getFolders(
		CommandArgument commandArgument, Document document, Node rootNode) {
	}

	protected void getFoldersAndFiles(
		CommandArgument commandArgument, Document document, Node rootNode) {

		try {
			_getFiles(commandArgument, document, rootNode);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	protected boolean isStagedData(Group group) {
		return group.isStagedPortlet(PortletKeys.WIKI);
	}

	private void _getFiles(
			CommandArgument commandArgument, Document document, Node rootNode)
		throws Exception {

		Element filesElement = document.createElement("Files");

		rootNode.appendChild(filesElement);

		HttpServletRequest request = commandArgument.getHttpServletRequest();

		long resourcePK = ParamUtil.getLong(request, "wikiPageResourcePrimKey");

		WikiPage page = WikiPageLocalServiceUtil.getPage(resourcePK);

		long repositoryId = CompanyConstants.SYSTEM;

		String dirName = page.getAttachmentsDir();

		String[] fileNames = null;

		try {
			fileNames = DLStoreUtil.getFileNames(
				page.getCompanyId(), repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
			DLStoreUtil.addDirectory(
				page.getCompanyId(), repositoryId, dirName);
			fileNames = DLStoreUtil.getFileNames(
				page.getCompanyId(), repositoryId, dirName);
		}

		for (String fileName : fileNames) {
			byte[] fileEntry = DLStoreUtil.getFile(
				page.getCompanyId(), repositoryId, fileName);

			String[] parts = StringUtil.split(fileName, StringPool.SLASH);
			fileName = parts[3];

			Element fileElement = document.createElement("File");

			filesElement.appendChild(fileElement);

			fileElement.setAttribute("name", fileName);
			fileElement.setAttribute("desc", fileName);
			fileElement.setAttribute("size", getSize(fileEntry.length));
			fileElement.setAttribute("url", fileName);
		}
	}

}