/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.InputStream;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Ivica Cardic
 */
public class PageCommandReceiver extends BaseCommandReceiver {

	@Override
	protected String createFolder(CommandArgument commandArgument) {
		return "0";
	}

	@Override
	protected String fileUpload(
		CommandArgument commandArgument, String fileName,
		InputStream inputStream, String extension, long size) {

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

	private void _getFiles(
			CommandArgument commandArgument, Document document, Node rootNode)
		throws Exception {

		if (commandArgument.getCurrentFolder().equals(StringPool.SLASH)) {
			return;
		}

		Element filesElement = document.createElement("Files");

		rootNode.appendChild(filesElement);

		Group group = commandArgument.getCurrentGroup();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (("/" + commandArgument.getCurrentGroupName() + "/").equals(
				commandArgument.getCurrentFolder())) {

			for (Layout layout : layouts) {
				Element fileElement = document.createElement("File");

				filesElement.appendChild(fileElement);

				fileElement.setAttribute("name", _getLayoutName(layout));
				fileElement.setAttribute("desc", _getLayoutName(layout));
				fileElement.setAttribute("size", StringPool.BLANK);
				fileElement.setAttribute(
					"url",
					PortalUtil.getLayoutURL(
						layout, commandArgument.getThemeDisplay(), false));
			}
		}
		else {
			String layoutName = _getLayoutName(
				commandArgument.getCurrentFolder());

			Layout layout = null;

			for (int i = 0; i < layouts.size(); i++) {
				layout = _getLayout(layoutName, layouts.get(i));

				if (layout != null) {
					break;
				}
			}

			if (layout == null) {
				return;
			}

			List<Layout> layoutChildren = layout.getChildren();

			for (int i = 0; i < layoutChildren.size(); i++) {
				layout = layoutChildren.get(i);

				Element fileElement = document.createElement("File");

				filesElement.appendChild(fileElement);

				fileElement.setAttribute("name", _getLayoutName(layout));
				fileElement.setAttribute("desc", _getLayoutName(layout));
				fileElement.setAttribute("size", getSize());
				fileElement.setAttribute(
					"url",
					PortalUtil.getLayoutURL(
						layout, commandArgument.getThemeDisplay(), false));
			}
		}
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

			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (("/" + commandArgument.getCurrentGroupName() + "/").equals(
					commandArgument.getCurrentFolder())) {

				for (Layout layout : layouts) {
					Element folderElement = document.createElement("Folder");

					foldersElement.appendChild(folderElement);

					folderElement.setAttribute(
						"name", "~" + _getLayoutName(layout).replace('/', '>'));
				}
			}
			else {
				String layoutName = _getLayoutName(
					commandArgument.getCurrentFolder());

				Layout layout = null;

				for (int i = 0; i < layouts.size(); i++) {
					layout = _getLayout(layoutName, layouts.get(i));

					if (layout != null) {
						break;
					}
				}

				if (layout != null) {
					List<Layout> layoutChildren = layout.getChildren();

					for (int i = 0; i < layoutChildren.size(); i++) {
						layout = layoutChildren.get(i);

						Element folderElement = document.createElement(
							"Folder");

						foldersElement.appendChild(folderElement);

						folderElement.setAttribute(
							"name",
							"~" + _getLayoutName(layout).replace('/', '>'));
					}
				}
			}
		}
	}

	private Layout _getLayout(String layoutName, Layout layout)
		throws Exception {

		String friendlyURL = layout.getFriendlyURL();

		if (layoutName.equals(friendlyURL)) {
			return layout;
		}

		List<Layout> layoutChildren = layout.getChildren();

		if (layoutChildren.size() == 0) {
			return null;
		}
		else {
			for (Layout layoutChild : layoutChildren) {
				Layout currentLayout = _getLayout(layoutName, layoutChild);

				if (currentLayout != null) {
					return currentLayout;
				}
			}
		}

		return null;
	}

	private String _getLayoutName(Layout layout) {
		return layout.getFriendlyURL();
	}

	private String _getLayoutName(String folderName) {
		String layoutName = folderName.substring(
			folderName.lastIndexOf('~') + 1, folderName.length() - 1);

		layoutName = layoutName.replace('>', '/');

		return layoutName;
	}

}