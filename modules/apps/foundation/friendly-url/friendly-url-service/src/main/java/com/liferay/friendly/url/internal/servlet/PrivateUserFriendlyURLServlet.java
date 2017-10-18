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

package com.liferay.friendly.url.internal.servlet;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.portal.servlet.friendly.url.PrivateUserFriendlyURLServlet",
		"osgi.http.whiteboard.servlet.pattern=/user/*",
		"servlet.init.private=true", "servlet.init.user=true",
		"servlet.type=friendly-url"
	},
	service = Servlet.class
)
public class PrivateUserFriendlyURLServlet extends FriendlyURLServlet {
}