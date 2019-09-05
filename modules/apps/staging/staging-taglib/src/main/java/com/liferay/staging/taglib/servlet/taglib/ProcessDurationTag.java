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
public class ProcessDurationTag extends IncludeTag {

	public BackgroundTask getBackgroundTask() {
		return _backgroundTask;
	}

	public boolean isListView() {
		return _listView;
	}

	public void setBackgroundTask(BackgroundTask backgroundTask) {
		_backgroundTask = backgroundTask;
	}

	public void setListView(boolean listView) {
		_listView = listView;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_backgroundTask = null;
		_listView = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-staging:process-duration:backgroundTask", _backgroundTask);
		httpServletRequest.setAttribute(
			"liferay-staging:process-duration:listView", _listView);
	}

	private static final String _PAGE = "/process_duration/page.jsp";

	private BackgroundTask _backgroundTask;
	private boolean _listView;

}