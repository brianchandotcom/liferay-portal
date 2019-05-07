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

package com.liferay.social.bookmarks.taglib.servlet.taglib;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.social.bookmarks.taglib.internal.servlet.ServletContextUtil;
import com.liferay.social.bookmarks.taglib.internal.util.SocialBookmarksRegistryUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class SocialBookmarksSettingsTag extends IncludeTag {

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getTypes() {
		return StringUtil.merge(_types);
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setTypes(String types) {
		if (types != null) {
			_types = StringUtil.split(types);
		}
		else {
			List<String> allTypes =
				SocialBookmarksRegistryUtil.getSocialBookmarksTypes();

			_types = allTypes.toArray(new String[0]);
		}
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
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-social-bookmarks:bookmarks-settings:displayStyle",
			_displayStyle);
		httpServletRequest.setAttribute(
			"liferay-social-bookmarks:bookmarks-settings:types", _types);
	}

	private static final String _PAGE = "/bookmarks_settings/page.jsp";

	private String _displayStyle;
	private String[] _types;

}