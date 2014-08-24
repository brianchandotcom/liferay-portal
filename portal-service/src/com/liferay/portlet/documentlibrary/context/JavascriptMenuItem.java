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

package com.liferay.portlet.documentlibrary.context;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

/**
 * @author Iván Zaera
 */
public class JavascriptMenuItem extends MenuItem {

	public JavascriptMenuItem(
		String id, String iconCssClass, String message, String onClick,
		Renderer javascriptRenderer) {

		super(id, _JSP_PATH, iconCssClass, message);

		_onClick = onClick;
		_javascriptRenderer = javascriptRenderer;
	}

	public Renderer getJavascriptRenderer() {
		return _javascriptRenderer;
	}

	public String getOnClick() {
		return _onClick;
	}

	public interface Renderer {
		public void render(PageContext pageContext)
			throws IOException, ServletException;
	}

	private static final String _JSP_PATH =
		"/html/portlet/document_library/display_context/" +
			"javascript_menu_item.jsp";

	private Renderer _javascriptRenderer;
	private String _onClick;

}