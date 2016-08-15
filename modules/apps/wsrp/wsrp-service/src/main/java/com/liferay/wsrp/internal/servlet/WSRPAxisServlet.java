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

package com.liferay.wsrp.internal.servlet;

import com.liferay.util.axis.AxisServlet;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "=(osgi.http.whiteboard.context.name=wsrp-service)",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME + "=com.liferay.wsrp.servlet.WSRPAxisServlet",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "=/services/*"
	},
	service = Servlet.class
)
public class WSRPAxisServlet extends AxisServlet {
}