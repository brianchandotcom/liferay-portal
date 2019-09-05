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

package com.liferay.staging.taglib.servlet.taglib;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Borkuti
 */
public class ProcessListMenuTag extends IncludeTag {

	public BackgroundTask getBackgroundTask() {
		return _backgroundTask;
	}

	public boolean isDeleteMenu() {
		return _deleteMenu;
	}

	public boolean isLocalPublishing() {
		return _localPublishing;
	}

	public boolean isRelaunchMenu() {
		return _relaunchMenu;
	}

	public boolean isSummaryMenu() {
		return _summaryMenu;
	}

	public void setBackgroundTask(BackgroundTask backgroundTask) {
		_backgroundTask = backgroundTask;
	}

	public void setDeleteMenu(boolean deleteMenu) {
		_deleteMenu = deleteMenu;
	}

	public void setLocalPublishing(boolean localPublishing) {
		_localPublishing = localPublishing;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setRelaunchMenu(boolean relaunchMenu) {
		_relaunchMenu = relaunchMenu;
	}

	public void setSummaryMenu(boolean summaryMenu) {
		_summaryMenu = summaryMenu;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_backgroundTask = null;
		_deleteMenu = true;
		_localPublishing = false;
		_relaunchMenu = true;
		_summaryMenu = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-staging:process-list-menu:backgroundTask",
			_backgroundTask);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list-menu:deleteMenu", _deleteMenu);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list-menu:localPublishing",
			_localPublishing);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list-menu:relaunchMenu", _relaunchMenu);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list-menu:summaryMenu", _summaryMenu);
	}

	private static final String _PAGE = "/process_list_menu/page.jsp";

	private BackgroundTask _backgroundTask;
	private boolean _deleteMenu = true;
	private boolean _localPublishing;
	private boolean _relaunchMenu = true;
	private boolean _summaryMenu = true;

}