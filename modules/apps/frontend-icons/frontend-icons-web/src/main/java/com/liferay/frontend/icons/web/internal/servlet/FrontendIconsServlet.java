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

package com.liferay.frontend.icons.web.internal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.PrintWriter;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/icons",
		"osgi.http.whiteboard.servlet.pattern=/icons/*"
	},
	service = Servlet.class
)
public class FrontendIconsServlet extends HttpServlet {

	@Override
	protected void doGet(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType(ContentTypes.IMAGE_SVG_XML);
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);

			PrintWriter printWriter = httpServletResponse.getWriter();

			String path = httpServletRequest.getPathInfo();

			Matcher matcher = _pattern.matcher(path);

			if (!matcher.matches() ||
				!Objects.equals(matcher.group(1), "clay")) {

				httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			else {
				String spritemap = StringUtil.read(
					FrontendIconsServlet.class,
					"/META-INF/resources/images/icons.svg");

				printWriter.write(spritemap);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			exception.printStackTrace();

			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendIconsServlet.class);

	private static final Pattern _pattern = Pattern.compile("^/(.*).svg");

	@Reference
	private Portal _portal;

}