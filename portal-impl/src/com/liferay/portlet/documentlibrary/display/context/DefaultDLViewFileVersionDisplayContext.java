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

package com.liferay.portlet.documentlibrary.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.display.context.helper.FileEntryDisplayContextHelper;
import com.liferay.portlet.documentlibrary.display.context.helper.FileVersionDisplayContextHelper;
import com.liferay.portlet.documentlibrary.display.context.util.JSPRenderer;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class DefaultDLViewFileVersionDisplayContext
	implements DLViewFileVersionDisplayContext {

	public DefaultDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			DLFileShortcut dlFileShortcut)
		throws PortalException {

		this(
			request, response, dlFileShortcut.getFileVersion(), dlFileShortcut);
	}

	public DefaultDLViewFileVersionDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		this(request, response, fileVersion, null);
	}

	@Override
	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException {

		DLFileEntryMetadata dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
				ddmStructure.getStructureId(), fileVersion.getFileVersionId());

		return StorageEngineUtil.getDDMFormValues(
			dlFileEntryMetadata.getDDMStorageId());
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		if (_fileVersionDisplayContextHelper.isDLFileVersion()) {
			DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

			return dlFileVersion.getDDMStructures();
		}

		return Collections.emptyList();
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = new Menu();

		String direction = "left";

		DLActionsDisplayContext dlActionsDisplayContext =
			_getDLActionsDisplayContext();

		if (dlActionsDisplayContext.isShowMinimalActionsButton()) {
			direction = "down";
		}

		menu.setDirection(direction);

		boolean extended = true;

		if (dlActionsDisplayContext.isShowMinimalActionsButton()) {
			extended = false;
		}

		menu.setExtended(extended);

		String icon = null;

		if (dlActionsDisplayContext.isShowMinimalActionsButton()) {
			icon = StringPool.BLANK;
		}

		menu.setIcon(icon);

		menu.setMenuItems(_getMenuItems());

		String message = "actions";

		if (dlActionsDisplayContext.isShowMinimalActionsButton()) {
			message = StringPool.BLANK;
		}

		menu.setMessage(message);

		menu.setShowWhenSingleIcon(
			dlActionsDisplayContext.isShowWhenSingleIconActionButton());
		menu.setTriggerCssClass("btn btn-default");

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = new ArrayList<>();

		uiItemsBuilder.addDownloadToolbarItem(toolbarItems);

		uiItemsBuilder.addOpenInMsOfficeToolbarItem(toolbarItems);

		uiItemsBuilder.addEditToolbarItem(toolbarItems);

		uiItemsBuilder.addMoveToolbarItem(toolbarItems);

		uiItemsBuilder.addCheckoutToolbarItem(toolbarItems);

		uiItemsBuilder.addCancelCheckoutToolbarItem(toolbarItems);

		uiItemsBuilder.addCheckinToolbarItem(toolbarItems);

		uiItemsBuilder.addPermissionsToolbarItem(toolbarItems);

		uiItemsBuilder.addMoveToTheRecycleBinToolbarItem(toolbarItems);

		uiItemsBuilder.addDeleteToolbarItem(toolbarItems);

		return toolbarItems;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isDownloadLinkVisible() throws PortalException {
		return _fileEntryDisplayContextHelper.isDownloadActionAvailable();
	}

	@Override
	public boolean isVersionInfoVisible() throws PortalException {
		return true;
	}

	@Override
	public void renderPreview(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		JSPRenderer jspRenderer = new JSPRenderer(
			"/html/portlet/document_library/view_file_entry_preview.jsp");

		jspRenderer.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, fileVersion);

		jspRenderer.render(request, response);
	}

	protected final FileEntry fileEntry;
	protected final FileVersion fileVersion;
	protected final HttpServletRequest request;
	protected final UIItemsBuilder uiItemsBuilder;

	private DefaultDLViewFileVersionDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion, DLFileShortcut dlFileShortcut) {

		try {
			this.request = request;
			this.fileVersion = fileVersion;

			FileEntry fileEntry = null;

			if (fileVersion != null) {
				fileEntry = fileVersion.getFileEntry();
			}

			this.fileEntry = fileEntry;

			_themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
				_themeDisplay.getPermissionChecker(), this.fileEntry);
			_fileVersionDisplayContextHelper =
				new FileVersionDisplayContextHelper(fileVersion);

			if (dlFileShortcut == null) {
				uiItemsBuilder = new UIItemsBuilder(
					request, response, fileVersion);
			}
			else {
				uiItemsBuilder = new UIItemsBuilder(
					request, response, dlFileShortcut);
			}
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to build BaseDefaultDLViewFileVersionDisplayContext " +
					"for " + fileVersion,
				pe);
		}
	}

	private DLActionsDisplayContext _getDLActionsDisplayContext()
		throws PortalException {

		if (_dlActionsDisplayContext == null) {
			_dlActionsDisplayContext =
				DLActionsDisplayContextUtil.getDLActionsDisplayContext(request);
		}

		return _dlActionsDisplayContext;
	}

	private List<MenuItem> _getMenuItems() throws PortalException {
		List<MenuItem> menuItems = new ArrayList<>();

		if (_isShowActions()) {
			uiItemsBuilder.addDownloadMenuItem(menuItems);

			uiItemsBuilder.addOpenInMsOfficeMenuItem(menuItems);

			uiItemsBuilder.addViewOriginalFileMenuItem(menuItems);

			uiItemsBuilder.addEditMenuItem(menuItems);

			uiItemsBuilder.addMoveMenuItem(menuItems);

			uiItemsBuilder.addCheckoutMenuItem(menuItems);

			uiItemsBuilder.addCheckinMenuItem(menuItems);

			uiItemsBuilder.addCancelCheckoutMenuItem(menuItems);

			uiItemsBuilder.addPermissionsMenuItem(menuItems);

			uiItemsBuilder.addDeleteMenuItem(menuItems);
		}

		return menuItems;
	}

	private boolean _isShowActions() throws PortalException {
		DLActionsDisplayContext dlActionsDisplayContext =
			_getDLActionsDisplayContext();

		return dlActionsDisplayContext.isShowActions();
	}

	private static final UUID _UUID = UUID.fromString(
		"85F6C50E-3893-4E32-9D63-208528A503FA");

	private DLActionsDisplayContext _dlActionsDisplayContext;
	private final FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;
	private final FileVersionDisplayContextHelper
		_fileVersionDisplayContextHelper;
	private final ThemeDisplay _themeDisplay;

}