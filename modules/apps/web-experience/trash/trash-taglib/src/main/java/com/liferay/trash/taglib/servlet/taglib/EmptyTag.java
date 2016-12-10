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

package com.liferay.trash.taglib.servlet.taglib;

import com.liferay.taglib.util.IncludeTag;
import com.liferay.trash.taglib.servlet.ServletContextUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * Creates a component for users to permanently delete items (articles, images,
 * etc.) from the Recycle Bin.
 *
 * @author Sergio González
 */
public class EmptyTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setConfirmMessage(String confirmMessage) {
		_confirmMessage = confirmMessage;
	}

	public void setEmptyMessage(String emptyMessage) {
		_emptyMessage = emptyMessage;
	}

	public void setInfoMessage(String infoMessage) {
		_infoMessage = infoMessage;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL.toString();
	}

	public void setPortletURL(String portletURL) {
		_portletURL = portletURL;
	}

	public void setTotalEntries(int totalEntries) {
		_totalEntries = totalEntries;
	}

	@Override
	protected void cleanUp() {
		_confirmMessage = _CONFIRM_MESSAGE;
		_emptyMessage = _EMPTY_MESSAGE;
		_infoMessage = _INFO_MESSAGE;
		_portletURL = null;
		_totalEntries = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-trash:empty:confirmMessage", _confirmMessage);
		request.setAttribute("liferay-trash:empty:emptyMessage", _emptyMessage);
		request.setAttribute("liferay-trash:empty:infoMessage", _infoMessage);
		request.setAttribute("liferay-trash:empty:portletURL", _portletURL);
		request.setAttribute("liferay-trash:empty:totalEntries", _totalEntries);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _CONFIRM_MESSAGE =
		"are-you-sure-you-want-to-empty-the-recycle-bin";

	private static final String _EMPTY_MESSAGE = "empty-the-recycle-bin";

	private static final String _INFO_MESSAGE =
		"entries-that-have-been-in-the-recycle-bin-for-more-than-x-are-" +
			"automatically-deleted";

	private static final String _PAGE = "/empty/page.jsp";

	private String _confirmMessage = _CONFIRM_MESSAGE;
	private String _emptyMessage = _EMPTY_MESSAGE;
	private String _infoMessage = _INFO_MESSAGE;
	private String _portletURL;
	private int _totalEntries;

}