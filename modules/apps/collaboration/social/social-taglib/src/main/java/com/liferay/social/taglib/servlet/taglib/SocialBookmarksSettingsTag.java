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

package com.liferay.social.taglib.servlet.taglib;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class SocialBookmarksSettingsTag extends IncludeTag {

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setTypes(String types) {
		_types = types;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_displayStyle = null;
		_types = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-social:bookmarks-settings:displayStyle", _displayStyle);
		request.setAttribute("liferay-social:bookmarks-settings:types", _types);
	}

	private static final String _PAGE = "/bookmarks_settings/page.jsp";

	private String _displayStyle;
	private String _types;

}