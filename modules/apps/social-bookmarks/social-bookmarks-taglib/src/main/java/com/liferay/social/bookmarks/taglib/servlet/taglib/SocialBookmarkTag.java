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

import com.liferay.social.bookmarks.SocialBookmark;
import com.liferay.social.bookmarks.taglib.internal.servlet.ServletContextUtil;
import com.liferay.social.bookmarks.taglib.internal.util.SocialBookmarksRegistryUtil;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author David Truong
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SocialBookmarkTag extends IncludeTag {

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

	public void setType(String type) {
		_type = type;
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_contentId = null;
		_target = null;
		_title = null;
		_type = null;
		_url = null;
	}

	@Override
	protected String getPage() {
		return "/bookmark/page.jsp";
	}

	@Override
	protected void includePage(String page, HttpServletResponse response)
		throws IOException, ServletException {

		SocialBookmark socialBookmark = _getSocialBookmark();

		if (socialBookmark != null) {
			if (_displayStyle.equals("menu")) {
				super.includePage(page, response);
			}
			else {
				socialBookmark.render(_target, _title, _url, request, response);
			}
		}
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-social-bookmarks:bookmark:contentId", _contentId);
		request.setAttribute(
			"liferay-social-bookmarks:bookmark:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-social-bookmarks:bookmark:socialBookmark",
			_getSocialBookmark());
		request.setAttribute(
			"liferay-social-bookmarks:bookmark:target", _target);
		request.setAttribute("liferay-social-bookmarks:bookmark:title", _title);
		request.setAttribute("liferay-social-bookmarks:bookmark:type", _type);
		request.setAttribute("liferay-social-bookmarks:bookmark:url", _url);
	}

	private SocialBookmark _getSocialBookmark() {
		return SocialBookmarksRegistryUtil.getSocialBookmark(_type);
	}

	private String _contentId;
	private String _displayStyle;
	private String _target;
	private String _title;
	private String _type;
	private String _url;

}