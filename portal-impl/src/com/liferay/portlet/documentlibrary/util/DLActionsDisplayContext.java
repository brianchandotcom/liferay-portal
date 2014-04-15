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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.trash.util.TrashUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
public class DLActionsDisplayContext {

	public DLActionsDisplayContext(HttpServletRequest request) {
		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_portletDisplay = themeDisplay.getPortletDisplay();
		_scopeGroupId = themeDisplay.getScopeGroupId();
	}

	public String getAllEntryColumns() throws PortalException, SystemException {
		String allEntryColumns = "name,size,status";

		if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
			allEntryColumns += ",downloads";
		}

		if (isShowActions()) {
			allEntryColumns += ",action";
		}

		allEntryColumns += ",modified-date,create-date";

		return allEntryColumns;
	}

	public String getAllFileEntryColumns()
		throws PortalException, SystemException {

		String allFileEntryColumns = "name,size";

		if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
			allFileEntryColumns += ",downloads";
		}

		allFileEntryColumns += ",locked";

		if (isShowActions()) {
			allFileEntryColumns += ",action";
		}

		return allFileEntryColumns;
	}

	public String getAllFolderColumns()
		throws PortalException, SystemException {

		String allFolderColumns = "name,num-of-folders,num-of-documents";

		if (isShowActions()) {
			allFolderColumns += ",action";
		}

		return allFolderColumns;
	}

	public String[] getEntryColumns() throws PortalException, SystemException {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			new DLPortletInstanceSettings(
				_portletDisplay.getPortletInstanceSettings());

		String[] entryColumns = StringUtil.split(
			dlPortletInstanceSettings.getEntryColumns());

		String portletId = _portletDisplay.getId();

		if (!isShowActions()) {
			entryColumns = ArrayUtil.remove(entryColumns, "action");
		}
		else if (!portletId.equals(PortletKeys.DOCUMENT_LIBRARY) &&
				 !portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN) &&
				 !ArrayUtil.contains(entryColumns, "action")) {

			entryColumns = ArrayUtil.append(entryColumns, "action");
		}

		return entryColumns;
	}

	public String[] getFileEntryColumns()
		throws PortalException, SystemException {

		DLPortletInstanceSettings dlPortletInstanceSettings =
			new DLPortletInstanceSettings(
				_portletDisplay.getPortletInstanceSettings());

		String[] fileEntryColumns = StringUtil.split(
			dlPortletInstanceSettings.getFileEntryColumns());

		if (!isShowActions()) {
			fileEntryColumns = ArrayUtil.remove(fileEntryColumns, "action");
		}

		return fileEntryColumns;
	}

	public String[] getFolderColumns() throws PortalException, SystemException {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			new DLPortletInstanceSettings(
				_portletDisplay.getPortletInstanceSettings());

		String[] folderColumns = StringUtil.split(
			dlPortletInstanceSettings.getFolderColumns());

		if (!isShowActions()) {
			folderColumns = ArrayUtil.remove(folderColumns, "action");
		}

		return folderColumns;
	}

	public boolean isAddFolderButtonVisible() {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	public boolean isFolderMenuVisible()
		throws PortalException, SystemException {

		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			new DLPortletInstanceSettings(
				_portletDisplay.getPortletInstanceSettings());

		return dlPortletInstanceSettings.getShowFolderMenu();
	}

	public boolean isIEOnWin32() {
		if (_ieOnWin32 == null) {
			_ieOnWin32 = BrowserSnifferUtil.isIeOnWin32(_request);
		}

		return _ieOnWin32;
	}

	public boolean isShowActions() throws PortalException, SystemException {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			new DLPortletInstanceSettings(
				_portletDisplay.getPortletInstanceSettings());

		return dlPortletInstanceSettings.getShowActions();
	}

	public boolean isShowMinimalActionsButton() {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return ParamUtil.getBoolean(_request, "showMinimalActionButtons");
	}

	public boolean isShowTabs() throws PortalException, SystemException {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			new DLPortletInstanceSettings(
				_portletDisplay.getPortletInstanceSettings());

		return dlPortletInstanceSettings.getShowTabs();
	}

	public boolean isShowWhenSingleIconActionButton() {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	public boolean isTrashEnabled() throws PortalException, SystemException {
		if (_trashEnabled == null) {
			_trashEnabled = TrashUtil.isTrashEnabled(_scopeGroupId);
		}

		return _trashEnabled;
	}

	public boolean isWebDAVEnabled() {
		return _portletDisplay.isWebDAVEnabled();
	}

	private Boolean _ieOnWin32;
	private PortletDisplay _portletDisplay;
	private HttpServletRequest _request;
	private long _scopeGroupId;
	private Boolean _trashEnabled;

}