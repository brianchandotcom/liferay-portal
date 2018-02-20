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
import com.liferay.social.bookmarks.SocialBookmark;
import com.liferay.social.bookmarks.taglib.internal.servlet.ServletContextUtil;
import com.liferay.social.bookmarks.taglib.internal.util.SocialBookmarkUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class SocialBookmarksTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		if (_getTypes().length == 0) {
			return EVAL_PAGE;
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		if (_getTypes().length == 0) {
			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public void setContentId(String contentId) {
		_contentId = contentId;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setTarget(String target) {
		_target = target;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setTypes(String types) {
		_types = StringUtil.split(types);
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_contentId = null;
		_displayStyle = null;
		_target = null;
		_title = null;
		_types = null;
		_url = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-social:bookmark:contentId", _contentId);
		request.setAttribute("liferay-social:bookmark:target", _target);
		request.setAttribute("liferay-social:bookmark:title", _title);
		request.setAttribute("liferay-social:bookmark:types", _getTypes());
		request.setAttribute("liferay-social:bookmark:url", _url);
		request.setAttribute(
			"liferay-social:bookmarks:displayStyle", _displayStyle);
	}

	private String[] _getTypes() {
		if (_types == null) {
			List<String> types = new ArrayList<>();

			for (SocialBookmark socialBookmark :
					SocialBookmarkUtil.getSocialBookmarks()) {

				types.add(socialBookmark.getType());
			}

			_types = types.toArray(new String[0]);
		}

		return _types;
	}

	private static final String _PAGE = "/bookmarks/page.jsp";

	private String _contentId;
	private String _displayStyle;
	private String _target;
	private String _title;
	private String[] _types;
	private String _url;

}