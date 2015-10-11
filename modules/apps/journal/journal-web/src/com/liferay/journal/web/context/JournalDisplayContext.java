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

package com.liferay.journal.web.context;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.web.configuration.JournalWebConfigurationValues;
import com.liferay.journal.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalDisplayContext {

	public JournalDisplayContext(
		HttpServletRequest request, PortletPreferences portletPreferences) {

		_request = request;
		_portletPreferences = portletPreferences;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(_request, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		if (_displayViews == null) {
			_displayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_portletPreferences, _request, "displayViews",
					StringUtil.merge(
						JournalWebConfigurationValues.DISPLAY_VIEWS)));
		}

		return _displayViews;
	}

	public JournalFolder getFolder() throws PortalException {
		if (_folder != null) {
			return _folder;
		}

		_folder = ActionUtil.getFolder(_request);

		return _folder;
	}

	public long getFolderId() throws PortalException {
		if (_folderId != null) {
			return _folderId;
		}

		JournalFolder folder = getFolder();

		_folderId = BeanParamUtil.getLong(
			folder, _request, "folderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_request, "navigation", "home");

		return _navigation;
	}

	public int getStatus() {
		if (_status != null) {
			return _status;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		int defaultStatus = WorkflowConstants.STATUS_APPROVED;

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isContentReviewer(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId()) ||
			isNavigationMine()) {

			defaultStatus = WorkflowConstants.STATUS_ANY;
		}

		_status = ParamUtil.getInteger(_request, "status", defaultStatus);

		return _status;
	}

	public boolean isNavigationHome() {
		if (getNavigation().equals("home")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationMine() {
		if (getNavigation().equals("mine")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationRecent() {
		if (getNavigation().equals("recent")) {
			return true;
		}

		return false;
	}

	public boolean isShowEditActions() {
		if (_showEditActions != null) {
			return _showEditActions;
		}

		_showEditActions = ParamUtil.getBoolean(
			_request, "showEditActions", true);

		return _showEditActions;
	}

	protected String getDisplayStyle(
		HttpServletRequest request, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		String displayStyle = ParamUtil.getString(request, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "display-style",
				JournalWebConfigurationValues.DEFAULT_DISPLAY_VIEW);
		}
		else {
			if (ArrayUtil.contains(displayViews, displayStyle)) {
				portalPreferences.setValue(
					JournalPortletKeys.JOURNAL, "display-style", displayStyle);
			}
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	private String _displayStyle;
	private String[] _displayViews;
	private JournalFolder _folder;
	private Long _folderId;
	private String _navigation;
	private final PortletPreferences _portletPreferences;
	private final HttpServletRequest _request;
	private Boolean _showEditActions;
	private Integer _status;

}