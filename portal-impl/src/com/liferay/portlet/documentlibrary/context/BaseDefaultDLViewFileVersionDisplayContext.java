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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.context.helper.FileEntryDisplayContextHelper;
import com.liferay.portlet.documentlibrary.context.helper.FileVersionDisplayContextHelper;
import com.liferay.portlet.documentlibrary.context.util.JSPRenderer;
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
public abstract class BaseDefaultDLViewFileVersionDisplayContext
	implements DLViewFileVersionDisplayContext {

	public BaseDefaultDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			DLFileShortcut dlFileShortcut)
		throws PortalException {

		this(
			request, response, dlFileShortcut.getFileVersion(), dlFileShortcut);
	}

	public BaseDefaultDLViewFileVersionDisplayContext(
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
	public List<MenuItem> getMenuItems() throws PortalException {
		List<MenuItem> menuItems = new ArrayList<>();

		if (_isShowActions()) {
			buildMenuItems(menuItems);
		}

		return menuItems;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = new ArrayList<>();

		_addDownloadToolbarItem(toolbarItems);

		_addOpenInMsOfficeToolbarItem(toolbarItems);

		_addEditToolbarItem(toolbarItems);

		_addMoveToolbarItem(toolbarItems);

		_addCheckoutToolbarItem(toolbarItems);

		_addCancelCheckoutToolbarItem(toolbarItems);

		_addCheckinToolbarItem(toolbarItems);

		_addPermissionsToolbarItem(toolbarItems);

		_addMoveToTheRecycleBinToolbarItem(toolbarItems);

		_addDeleteToolbarItem(toolbarItems);

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

	protected void addCancelCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		_uiItemsBuilder.addCancelCheckoutMenuItem(menuItems);
	}

	protected void addCheckinMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		_uiItemsBuilder.addCheckinMenuItem(menuItems);
	}

	protected void addCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		_uiItemsBuilder.addCheckoutMenuItem(menuItems);
	}

	protected void addDeleteMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		_uiItemsBuilder.addDeleteMenuItem(menuItems);
	}

	protected void addDownloadMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		_uiItemsBuilder.addDownloadMenuItem(menuItems);
	}

	protected void addEditMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		_uiItemsBuilder.addEditMenuItem(menuItems);
	}

	protected void addMoveMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		_uiItemsBuilder.addMoveMenuItem(menuItems);
	}

	protected void addOpenInMsOfficeMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		_uiItemsBuilder.addOpenInMsOfficeMenuItem(menuItems);
	}

	protected void addPermissionsMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		_uiItemsBuilder.addPermissionsMenuItem(menuItems);
	}

	protected void addViewOriginalFileMenuItem(List<MenuItem> menuItems) {
		_uiItemsBuilder.addViewOriginalFileMenuItem(menuItems);
	}

	protected abstract void buildMenuItems(List<MenuItem> menuItems)
		throws PortalException;

	protected final FileEntry fileEntry;
	protected final FileVersion fileVersion;
	protected final HttpServletRequest request;

	private BaseDefaultDLViewFileVersionDisplayContext(
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
				_uiItemsBuilder = new UIItemsBuilder(
					request, response, fileVersion);
			}
			else {
				_uiItemsBuilder = new UIItemsBuilder(
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

	private void _addCancelCheckoutToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		_uiItemsBuilder.addCancelCheckoutToolbarItem(toolbarItems);
	}

	private void _addCheckinToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		_uiItemsBuilder.addCheckinToolbarItem(toolbarItems);
	}

	private void _addCheckoutToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		_uiItemsBuilder.addCheckoutToolbarItem(toolbarItems);
	}

	private void _addDeleteToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		_uiItemsBuilder.addDeleteToolbarItem(toolbarItems);
	}

	private void _addDownloadToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		_uiItemsBuilder.addDownloadToolbarItem(toolbarItems);
	}

	private void _addEditToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		_uiItemsBuilder.addEditToolbarItem(toolbarItems);
	}

	private void _addMoveToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		_uiItemsBuilder.addMoveToolbarItem(toolbarItems);
	}

	private void _addMoveToTheRecycleBinToolbarItem(
			List<ToolbarItem> toolbarItems)
		throws PortalException {

		_uiItemsBuilder.addMoveToTheRecycleBinToolbarItem(toolbarItems);
	}

	private void _addOpenInMsOfficeToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		_uiItemsBuilder.addOpenInMsOfficeToolbarItem(toolbarItems);
	}

	private void _addPermissionsToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		_uiItemsBuilder.addPermissionsToolbarItem(toolbarItems);
	}

	private DLActionsDisplayContext _getDLActionsDisplayContext()
		throws PortalException {

		if (_dlActionsDisplayContext != null) {
			return _dlActionsDisplayContext;
		}

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String portletId = portletDisplay.getId();

		if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
			portletId = ParamUtil.getString(request, "portletResource");
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			DLPortletInstanceSettings.getInstance(
				_themeDisplay.getLayout(), portletId);

		_dlActionsDisplayContext = new DLActionsDisplayContext(
			request, dlPortletInstanceSettings);

		return _dlActionsDisplayContext;
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
	private final UIItemsBuilder _uiItemsBuilder;

}