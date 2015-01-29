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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.display.context.logic.DLPortletInstanceSettingsHelper;
import com.liferay.portlet.documentlibrary.display.context.util.DLRequestHelper;

/**
 * @author Iván Zaera
 */
public class DLEntryListDisplayContext {

	public DLEntryListDisplayContext(DLRequestHelper dlRequestHelper) {
		_dlRequestHelper = dlRequestHelper;

		_dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(
			_dlRequestHelper);
	}

	public String[] getEntryColumns() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		String[] entryColumns = dlPortletInstanceSettings.getEntryColumns();

		String portletName = _dlRequestHelper.getPortletName();

		if (!_dlPortletInstanceSettingsHelper.isShowActions()) {
			entryColumns = ArrayUtil.remove(entryColumns, "action");
		}
		else if (!portletName.equals(PortletKeys.DOCUMENT_LIBRARY) &&
				 !portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN) &&
				 !ArrayUtil.contains(entryColumns, "action")) {

			entryColumns = ArrayUtil.append(entryColumns, "action");
		}

		return entryColumns;
	}

	public String[] getFileEntryColumns() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		String[] fileEntryColumns =
			dlPortletInstanceSettings.getFileEntryColumns();

		if (!_dlPortletInstanceSettingsHelper.isShowActions()) {
			fileEntryColumns = ArrayUtil.remove(fileEntryColumns, "action");
		}

		return fileEntryColumns;
	}

	public String[] getFolderColumns() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		String[] folderColumns = dlPortletInstanceSettings.getFolderColumns();

		if (!_dlPortletInstanceSettingsHelper.isShowActions()) {
			folderColumns = ArrayUtil.remove(folderColumns, "action");
		}

		return folderColumns;
	}

	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;
	private final DLRequestHelper _dlRequestHelper;

}