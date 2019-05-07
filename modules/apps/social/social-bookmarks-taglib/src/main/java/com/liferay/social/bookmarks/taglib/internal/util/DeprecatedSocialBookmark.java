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

package com.liferay.social.bookmarks.taglib.internal.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.social.bookmarks.SocialBookmark;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alejandro Tardín
 */
public class DeprecatedSocialBookmark implements SocialBookmark {

	public DeprecatedSocialBookmark(String type) {
		_type = type;
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, _type);
	}

	@Override
	public String getPostURL(String title, String url) {
		return StringUtil.replace(
			PropsUtil.get(_SOCIAL_BOOKMARK_POST_URL, new Filter(_type)),
			new String[] {
				"${liferay:social-bookmark:title}",
				"${liferay:social-bookmark:url}"
			},
			new String[] {URLCodec.encodeURL(title), url});
	}

	@Override
	public void render(
			String target, String title, String url,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			httpServletRequest.getRequestDispatcher(
				"/bookmark/deprecated_bookmark.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	private static final String _SOCIAL_BOOKMARK_POST_URL =
		"social.bookmark.post.url";

	private final String _type;

}