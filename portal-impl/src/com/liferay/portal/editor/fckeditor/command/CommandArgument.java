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

package com.liferay.portal.editor.fckeditor.command;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class CommandArgument {

	public CommandArgument(
		String command, String type, String currentFolder, String newFolder,
		ThemeDisplay themeDisplay, HttpServletRequest request) {

		_command = command;
		_type = type;
		_currentFolder = currentFolder;
		_newFolder = newFolder;
		_themeDisplay = themeDisplay;
		_request = request;
	}

	public String getCommand() {
		return _command;
	}

	public long getCompanyId() {
		return _themeDisplay.getCompanyId();
	}

	public String getCurrentFolder() {
		return _currentFolder;
	}

	public Group getCurrentGroup() throws Exception {
		String currentGroupName = getCurrentGroupName();

		int pos = currentGroupName.indexOf(" - ");

		if (pos > 0) {
			long groupId = GetterUtil.getLong(
				currentGroupName.substring(0, pos));

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.getCompanyId() == getCompanyId()) {
				return group;
			}
		}

		throw new NoSuchGroupException();
	}

	public String getCurrentGroupName() {
		if (_currentFolder.equals("/")) {
			return StringPool.BLANK;
		}

		StringTokenizer st = new StringTokenizer(_currentFolder, "/");

		return st.nextToken();
	}

	public HttpServletRequest getHttpServletRequest() {
		return _request;
	}

	public Locale getLocale() {
		return _themeDisplay.getLocale();
	}

	public String getNewFolder() {
		return _newFolder;
	}

	public long getPlid() throws Exception {
		long plid = _themeDisplay.getPlid();

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		Group group = getCurrentGroup();

		if (layout.getGroupId() != group.getGroupId()) {
			plid = LayoutLocalServiceUtil.getDefaultPlid(group.getGroupId());
		}

		return plid;
	}

	public ThemeDisplay getThemeDisplay() {
		return _themeDisplay;
	}

	public String getType() {
		return _type;
	}

	public long getUserId() {
		return _themeDisplay.getUserId();
	}

	private String _command;
	private String _currentFolder;
	private String _newFolder;
	private HttpServletRequest _request;
	private ThemeDisplay _themeDisplay;
	private String _type;

}