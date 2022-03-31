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

import com.liferay.frontend.icons.web.internal.model.FrontendIconsResourcePack;
import com.liferay.frontend.icons.web.internal.repository.FrontendIconsResourcePackRepository;
import com.liferay.frontend.icons.web.internal.util.SVGUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.PrintWriter;

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

			Matcher matcher = _pattern.matcher(
				httpServletRequest.getPathInfo());

			if (!matcher.matches()) {
				httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

				return;
			}

			FrontendIconsResourcePack frontendIconsResourcePack =
				_frontendIconsResourcePackRepository.
					getFrontendIconsResourcePack(
						(Long)httpServletRequest.getAttribute(
							WebKeys.COMPANY_ID),
						matcher.group(1));

			if (frontendIconsResourcePack == null) {
				httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

				return;
			}

			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write(
				SVGUtil.getSVGSpritemap(frontendIconsResourcePack));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendIconsServlet.class);

	private static final Pattern _pattern = Pattern.compile("^/(.*).svg");

	@Reference
	private FrontendIconsResourcePackRepository
		_frontendIconsResourcePackRepository;

	@Reference
	private Portal _portal;

}