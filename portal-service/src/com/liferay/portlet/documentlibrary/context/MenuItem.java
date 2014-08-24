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
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Iván Zaera
 */
public abstract class MenuItem {

	public MenuItem(
		String id, String jspPath, String iconCssClass, String message) {

		_id = id;
		_jspPath = jspPath;
		_iconCssClass = iconCssClass;
		_message = message;
	}

	public String getIconCssClass() {
		return _iconCssClass;
	}

	public String getId() {
		return _id;
	}

	public String getMessage() {
		return _message;
	}

	public void render(PageContext pageContext)
		throws IOException, ServletException {

		ServletRequest request = pageContext.getRequest();

		request.setAttribute(URLMenuItem.class.getName(), this);

		pageContext.include(_jspPath);
	}

	private String _iconCssClass;
	private String _id;
	private String _jspPath;
	private String _message;

}