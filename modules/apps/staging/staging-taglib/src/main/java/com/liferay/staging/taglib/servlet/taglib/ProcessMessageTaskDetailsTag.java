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

import com.liferay.petra.string.StringPool;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Borkuti
 */
public class ProcessMessageTaskDetailsTag extends IncludeTag {

	public long getBackgroundTaskId() {
		return _backgroundTaskId;
	}

	public String getBackgroundTaskStatusMessage() {
		return _backgroundTaskStatusMessage;
	}

	public String getLinkClass() {
		return _linkClass;
	}

	public void setBackgroundTaskId(long backgroundTaskId) {
		_backgroundTaskId = backgroundTaskId;
	}

	public void setBackgroundTaskStatusMessage(
		String backgroundTaskStatusMessage) {

		_backgroundTaskStatusMessage = backgroundTaskStatusMessage;
	}

	public void setLinkClass(String linkClass) {
		_linkClass = linkClass;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_backgroundTaskId = 0;
		_backgroundTaskStatusMessage = null;
		_linkClass = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-staging:process-message-task-details:backgroundTaskId",
			_backgroundTaskId);
		httpServletRequest.setAttribute(
			"liferay-staging:" +
				"process-message-task-details:backgroundTaskStatusMessage",
			_backgroundTaskStatusMessage);
		httpServletRequest.setAttribute(
			"liferay-staging:process-message-task-details:linkClass",
			_linkClass);
	}

	private static final String _PAGE =
		"/process_message_task_details/page.jsp";

	private long _backgroundTaskId;
	private String _backgroundTaskStatusMessage;
	private String _linkClass = StringPool.BLANK;

}