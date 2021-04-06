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

package com.liferay.social.bookmark.deprecated.internal;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
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

import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseDeprecatedSocialBookmark implements SocialBookmark {

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return language.get(resourceBundle, getType());
	}

	@Override
	public String getPostURL(String title, String url) {
		return StringUtil.replace(
			getPostURL(),
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
			servletContext.getRequestDispatcher("/deprecated_bookmark.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	protected abstract String getPostURL();

	protected abstract String getType();

	@Reference
	protected Language language;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.social.bookmark.deprecated)"
	)
	protected ResourceBundleLoader resourceBundleLoader;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.social.bookmark.deprecated)"
	)
	protected ServletContext servletContext;

}