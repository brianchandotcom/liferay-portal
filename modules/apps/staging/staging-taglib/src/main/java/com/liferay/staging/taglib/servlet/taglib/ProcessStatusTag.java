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

import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Borkuti
 */
public class ProcessStatusTag extends IncludeTag {

	public int getBackgroundTaskStatus() {
		return _backgroundTaskStatus;
	}

	public String getBackgroundTaskStatusLabel() {
		return _backgroundTaskStatusLabel;
	}

	public void setBackgroundTaskStatus(int backgroundTaskStatus) {
		_backgroundTaskStatus = backgroundTaskStatus;
	}

	public void setBackgroundTaskStatusLabel(String backgroundTaskStatusLabel) {
		_backgroundTaskStatusLabel = backgroundTaskStatusLabel;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_backgroundTaskStatus = 0;
		_backgroundTaskStatusLabel = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-staging:process-status:backgroundTaskStatus",
			_backgroundTaskStatus);
		httpServletRequest.setAttribute(
			"liferay-staging:process-status:backgroundTaskStatusLabel",
			_backgroundTaskStatusLabel);
	}

	private static final String _PAGE = "/process_status/page.jsp";

	private int _backgroundTaskStatus;
	private String _backgroundTaskStatusLabel;

}