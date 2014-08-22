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

/**
 * @author Iván Zaera
 */
public class URLMenuItem extends MenuItem {

	public URLMenuItem(
		String id, String iconCssClass, String message, String url) {

		this(id, iconCssClass, message, url, "_self");
	}

	public URLMenuItem(
		String id, String iconCssClass, String message, String url,
		String target) {

		super(id, _JSP_PATH, iconCssClass, message);

		_url = url;
		_target = target;
	}

	public String getTarget() {
		return _target;
	}

	public String getURL() {
		return _url;
	}

	private static final String _JSP_PATH =
		"/html/portlet/document_library/display_context/url_menu_item.jsp";

	private String _target;
	private String _url;

}