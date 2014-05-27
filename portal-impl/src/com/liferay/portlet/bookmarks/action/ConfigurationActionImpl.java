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

package com.liferay.portlet.bookmarks.action;

import com.liferay.portal.kernel.portlet.SettingsConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Sergio González
 */
public class ConfigurationActionImpl extends SettingsConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateEmail(actionRequest, "emailMessageAdded");
		validateEmail(actionRequest, "emailMessageUpdated");
		validateEmailFrom(actionRequest);
		validateRootFolder(actionRequest);

		updateEntryColumns(actionRequest);
		updateFolderColumns(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected void updateEntryColumns(ActionRequest actionRequest) {
		String entryColumns = getParameter(actionRequest, "entryColumns");

		setPreference(
			actionRequest, "entryColumns", StringUtil.split(entryColumns));
	}

	protected void updateFolderColumns(ActionRequest actionRequest) {
		String folderColumns = getParameter(actionRequest, "folderColumns");

		setPreference(
			actionRequest, "folderColumns", StringUtil.split(folderColumns));
	}

	protected void validateRootFolder(ActionRequest actionRequest)
		throws Exception {

		long rootFolderId = GetterUtil.getLong(
			getParameter(actionRequest, "rootFolderId"));

		if (rootFolderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			try {
				BookmarksFolderLocalServiceUtil.getFolder(rootFolderId);
			}
			catch (NoSuchFolderException nsfe) {
				SessionErrors.add(actionRequest, "rootFolderIdInvalid");
			}
		}
	}

}