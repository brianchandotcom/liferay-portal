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
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import java.io.InputStream;

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

	@Override
	protected String createFolder(CommandArgument commandArgument) {
		return "0";
	}

	@Override
	protected String fileUpload(
		CommandArgument commandArgument, String fileName,
		InputStream inputStream, String extension, long size) {

		try {
			HttpServletRequest request =
				commandArgument.getHttpServletRequest();

			long resourcePK = ParamUtil.getLong(
				request, "wikiPageResourcePrimKey");

			WikiPage page = WikiPageLocalServiceUtil.getPage(resourcePK);

			String title = page.getTitle();

			long nodeId = page.getNodeId();

			List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
				new ArrayList<ObjectValuePair<String, InputStream>>(1);

			ObjectValuePair<String, InputStream> inputStreamOVP =
				new ObjectValuePair<String, InputStream>(fileName, inputStream);

			inputStreamOVPs.add(inputStreamOVP);

			WikiPageServiceUtil.addPageAttachments(
				nodeId, title, inputStreamOVPs);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new FCKException(e);
		}

		return "0";
	}

	@Override
	protected void getFolders(
		CommandArgument commandArgument, Document document, Node rootNode) {
	}

	@Override
	protected void getFoldersAndFiles(
		CommandArgument commandArgument, Document document, Node rootNode) {

		try {
			_getFiles(commandArgument, document, rootNode);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new FCKException(e);
		}
	}

	@Override
	protected boolean isStagedData(Group group) {
		return group.isStagedPortlet(PortletKeys.WIKI);
	}

	private void _getFiles(
			CommandArgument commandArgument, Document document, Node rootNode)
		throws Exception {

		Element filesElement = document.createElement("Files");

		rootNode.appendChild(filesElement);

		HttpServletRequest request = commandArgument.getHttpServletRequest();

		long wikiPageResourcePrimKey = ParamUtil.getLong(
			request, "wikiPageResourcePrimKey");

		WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(
			wikiPageResourcePrimKey);

		String attachmentURLPrefix = ParamUtil.getString(
			request, "attachmentURLPrefix");

		for (FileEntry fileEntry : wikiPage.getAttachmentsFileEntries()) {
			Element fileElement = document.createElement("File");

			filesElement.appendChild(fileElement);

			fileElement.setAttribute("name", fileEntry.getTitle());
			fileElement.setAttribute("desc", fileEntry.getTitle());
			fileElement.setAttribute(
				"size", String.valueOf(fileEntry.getSize()));
			fileElement.setAttribute(
				"url", attachmentURLPrefix + fileEntry.getTitle());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
			AttachmentCommandReceiver.class);

}