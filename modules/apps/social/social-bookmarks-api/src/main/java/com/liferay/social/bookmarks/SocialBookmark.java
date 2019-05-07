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

package com.liferay.social.bookmarks;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides a specialized interface to define a social bookmark.
 *
 * <p>
 * Every OSGi service registered with this interface is available in the social
 * bookmarks configuration menu. When registering an implementation, the
 * property {@code social.bookmarks.type} must be set to a unique key
 * identifying the sharing service (e.g., {@code facebook}). If two services
 * share the same value for this property, the one with the highest service
 * ranking is used.
 * </p>
 *
 * @author Alejandro Tardín
 */
public interface SocialBookmark {

	/**
	 * Returns the social bookmark's name. This name is displayed in settings,
	 * tooltips, etc.
	 *
	 * @param  locale the requested locale of the message
	 * @return the social bookmark's name
	 */
	public String getName(Locale locale);

	/**
	 * Returns the URL that users are redirected to when clicking the social
	 * bookmark.
	 *
	 * @param  title the title of the content being shared
	 * @param  url the URL of the content being shared (e.g., the current page)
	 * @return the URL that users are redirected to when clicking the social
	 *         bookmark
	 */
	public String getPostURL(String title, String url);

	/**
	 * Renders the social bookmark's content. This method is called when using
	 * the {@code inline} display style.
	 *
	 * <p>
	 * This typically renders a link to the sharing URL with a custom icon or
	 * image. However, if the sharing platform provides code to display the
	 * bookmark, it can also be rendered from this method.
	 * </p>
	 *
	 * @param  target the desired target for the link (e.g., {@code _blank})
	 * @param  title the title of the content being shared
	 * @param  url the URL of the content being shared (e.g., the current page)
	 * @param  httpServletRequest the servlet request
	 * @param  httpServletResponse the servlet response
	 * @throws IOException if an IO exception occurred
	 * @throws ServletException if a servlet exception occurred
	 */
	public void render(
			String target, String title, String url,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException;

}