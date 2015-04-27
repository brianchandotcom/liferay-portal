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

package com.liferay.wiki.engine.impl;

import com.liferay.wiki.engine.BaseWikiEngine;
import com.liferay.wiki.model.WikiPage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Iván Zaera
 */
public abstract class BaseInputEditorWikiEngine extends BaseWikiEngine {

	public static BaseInputEditorWikiEngine getBaseInputEditorWikiEngine(
		ServletRequest servletRequest) {

		return (BaseInputEditorWikiEngine)servletRequest.getAttribute(
			_WIKI_ENGINE);
	}

	public String getHelpPage() {
		return _helpPage;
	}

	public String getHelpURL() {
		return _helpURL;
	}

	@Override
	public void renderEditPage(
			ServletRequest servletRequest, ServletResponse servletResponse,
			WikiPage page)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			servletRequest.getRequestDispatcher(
				"/o/wiki-web/html/portlet/wiki/edit/wiki.jsp");

		servletRequest.setAttribute(_WIKI_ENGINE, this);

		super.renderEditPage(servletRequest, servletResponse, page);
	}

	protected BaseInputEditorWikiEngine(String helpPage, String helpURL) {
		_helpPage = helpPage;
		_helpURL = helpURL;
	}

	private static final String _WIKI_ENGINE =
		BaseInputEditorWikiEngine.class.getName() + "#wikiEngine";

	private final String _helpPage;
	private final String _helpURL;

}