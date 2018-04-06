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
 * Every OSGi service registered to this interface will be ready to use in the
 * social bookmarks configuration menu.
 *
 * Implementations must be registered with a value set for the property
 * 'social.bookmarks.type' indicating a unique key by which the sharing service
 * can be identified (e.g. facebook). If 2 services share the same value for the
 * property, the one wich a highest service ranking will be chosen.
 *
 * @author Alejandro Tardín
 */
public interface SocialBookmark {

	/**
	 * Must return the name of the social bookmark. It is the name that will be
	 * displayed in places like settings, tooltips, etc...
	 *
	 * @param locale the requested locale of the message
	 */
	public String getName(Locale locale);

	/**
	 * Must return the URL responsible for sharing the content. This is the URL that
	 * users will be redirected to when clicking on the social bookmark.
	 *
	 * @param title the title of the content being shared
	 * @param url the url of the content being shared (e.g. current page)
	 */
	public String getPostURL(String title, String url);

	/**
	 * This method is responsible for rendering the content of the social
	 * bookmark. It will be called when the 'inline' display style is configured.
	 *
	 * This will typically render a link with a custom icon or image pointing to
	 * the saring URL. However if the sharing platform used provides their own
	 * code to display the bookmark it can also be rendered from this method.
	 *
	 * @param target the desired target for the link (e.g. _blank)
	 * @param title the title of the content being shared
	 * @param url the url of the content being shared (e.g. current page)
	 * @param request the servlet request to obtain any useful information from the current page
	 * @param response the servlet response to write the render result
	 *
	 * @throws IOException
	 * @throws ServletException
	 */
	public void render(
			String target, String title, String url, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException;

}