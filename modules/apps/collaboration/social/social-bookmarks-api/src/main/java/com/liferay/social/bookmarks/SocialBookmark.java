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
 * Every OSGi service registered with this interface is available in the social
 * bookmarks configuration menu. When registering an implementation, the
 * property 'social.bookmarks.type' must be set to a unique key identifying the
 * sharing service (e.g. facebook). If two services share the same value for
 * this property, the one with the highest service ranking is used.
 *
 * @author Alejandro Tardín
 */
public interface SocialBookmark {

	/**
	 * Returns the social bookmark's name. This name is displayed in settings,
	 * tooltips, and so on.
	 *
	 * @param locale the requested locale of the message
	 */
	public String getName(Locale locale);

	/**
	 * Returns the URL that users are redirected to when clicking the social
	 * bookmark.
	 *
	 * @param title the title of the content being shared
	 * @param url the URL of the content being shared (e.g., the current page)
	 */
	public String getPostURL(String title, String url);

	/**
	 * Renders the social bookmark's content. This method is called when using
	 * the {@code inline} display style.
	 *
	 * This typically renders a link to the sharing URL with a custom icon or
	 * image. However, if the sharing platform provides code to display the
	 * bookmark it can also be rendered from this method.
	 *
	 * @param  target the desired target for the link (e.g. _blank)
	 * @param  title the title of the content being shared
	 * @param  url the URL of the content being shared (e.g., the current page)
	 * @param  request the servlet request
	 * @param  response the servlet response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void render(
			String target, String title, String url, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException;

}