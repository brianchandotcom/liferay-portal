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

package com.liferay.social.bookmark.plusone;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
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
	property =
		{"social.bookmarks.priority:Integer=0", "social.bookmarks.type=plusone"}
)
public class PlusoneSocialBookmark implements SocialBookmark {

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "plusone");
	}

	@Override
	public String getPostURL(String title, String url) {
		return String.format("https://plus.google.com/share?url=%s", url);
	}

	@Override
	public void render(
			String target, String title, String url, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/page.jsp");

		requestDispatcher.include(request, response);
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.social.bookmark.plusone)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.social.bookmark.plusone)"
	)
	private ServletContext _servletContext;

}