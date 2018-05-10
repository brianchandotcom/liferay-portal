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

package com.liferay.journal.taglib.servlet.taglib;

import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Alejandro Tardín
 */
public class JournalArticleDisplayTag extends IncludeTag {

	public void setArticleDisplay(JournalArticleDisplay articleDisplay) {
		_articleDisplay = articleDisplay;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setShowTitle(boolean showTitle) {
		_showTitle = showTitle;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_articleDisplay = null;
		_showTitle = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-journal:journal-article:articleDisplay", _articleDisplay);
		request.setAttribute(
			"liferay-journal:journal-article:showTitle",
			String.valueOf(_showTitle));
	}

	private static final String _PAGE = "/journal_article/page.jsp";

	private JournalArticleDisplay _articleDisplay;
	private boolean _showTitle;

}