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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class DLConfigurationDisplayContext {

	public DLConfigurationDisplayContext(
		HttpServletRequest request,
		DLPortletInstanceSettings dlPortletInstanceSettings) {

		_dlActionsDisplayContext = new DLActionsDisplayContext(
			request, dlPortletInstanceSettings);
	}

	public String[] getAllEntryColumns()
		throws PortalException, SystemException {

		String allEntryColumns = "name,size,status";

		if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
			allEntryColumns += ",downloads";
		}

		if (_dlActionsDisplayContext.isShowActions()) {
			allEntryColumns += ",action";
		}

		allEntryColumns += ",modified-date,create-date";

		return StringUtil.split(allEntryColumns);
	}

	public String[] getAllFileEntryColumns()
		throws PortalException, SystemException {

		String allFileEntryColumns = "name,size";

		if (PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED) {
			allFileEntryColumns += ",downloads";
		}

		allFileEntryColumns += ",locked";

		if (_dlActionsDisplayContext.isShowActions()) {
			allFileEntryColumns += ",action";
		}

		return StringUtil.split(allFileEntryColumns);
	}

	public String[] getAllFolderColumns()
		throws PortalException, SystemException {

		String allFolderColumns = "name,num-of-folders,num-of-documents";

		if (_dlActionsDisplayContext.isShowActions()) {
			allFolderColumns += ",action";
		}

		return StringUtil.split(allFolderColumns);
	}

	public DLActionsDisplayContext getDLActionsDisplayContext() {
		return _dlActionsDisplayContext;
	}

	private DLActionsDisplayContext _dlActionsDisplayContext;

}