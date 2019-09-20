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

package com.liferay.social.bookmark.linkedin;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.social.bookmarks.SocialBookmark;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"social.bookmarks.priority:Integer=1", "social.bookmarks.type=linkedin"
	},
	service = SocialBookmark.class
)
public class LinkedInSocialBookmark implements SocialBookmark {

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, LinkedInSocialBookmark.class);

		return LanguageUtil.get(resourceBundle, "linkedin");
	}

	@Override
	public String getPostURL(String title, String url) {
		return String.format(
			"http://www.linkedin.com/shareArticle?title=%s&mini=true&url=%s" +
				"&summary=",
			URLCodec.encodeURL(title), URLCodec.encodeURL(url));
	}

	@Override
	public void render(
			String target, String title, String url,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/page.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.social.bookmark.linkedin)"
	)
	private ServletContext _servletContext;

}