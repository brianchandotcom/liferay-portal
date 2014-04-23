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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class DLEntryListDisplayContext {

	public DLEntryListDisplayContext(
		HttpServletRequest request,
		DLPortletInstanceSettings dlPortletInstanceSettings) {

		_dlPortletInstanceSettings = dlPortletInstanceSettings;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_portletDisplay = themeDisplay.getPortletDisplay();

		_dlActionsDisplayContext = new DLActionsDisplayContext(
			request, dlPortletInstanceSettings);
	}

	public String getAllEntryColumns() throws PortalException, SystemException {
		String allEntryColumns = "name,size,status";

		if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
			allEntryColumns += ",downloads";
		}

		if (_dlActionsDisplayContext.isShowActions()) {
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

		if (_dlActionsDisplayContext.isShowActions()) {
			allFileEntryColumns += ",action";
		}

		return allFileEntryColumns;
	}

	public String getAllFolderColumns()
		throws PortalException, SystemException {

		String allFolderColumns = "name,num-of-folders,num-of-documents";

		if (_dlActionsDisplayContext.isShowActions()) {
			allFolderColumns += ",action";
		}

		return allFolderColumns;
	}

	public DLActionsDisplayContext getDLActionsDisplayContext() {
		return _dlActionsDisplayContext;
	}

	public String[] getEntryColumns() throws PortalException, SystemException {
		String[] entryColumns = StringUtil.split(
			_dlPortletInstanceSettings.getEntryColumns());

		String portletId = _portletDisplay.getId();

		if (!_dlActionsDisplayContext.isShowActions()) {
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

		String[] fileEntryColumns = StringUtil.split(
			_dlPortletInstanceSettings.getFileEntryColumns());

		if (!_dlActionsDisplayContext.isShowActions()) {
			fileEntryColumns = ArrayUtil.remove(fileEntryColumns, "action");
		}

		return fileEntryColumns;
	}

	public String[] getFolderColumns() throws PortalException, SystemException {
		String[] folderColumns = StringUtil.split(
			_dlPortletInstanceSettings.getFolderColumns());

		if (!_dlActionsDisplayContext.isShowActions()) {
			folderColumns = ArrayUtil.remove(folderColumns, "action");
		}

		return folderColumns;
	}

	private DLActionsDisplayContext _dlActionsDisplayContext;
	private DLPortletInstanceSettings _dlPortletInstanceSettings;
	private PortletDisplay _portletDisplay;

}