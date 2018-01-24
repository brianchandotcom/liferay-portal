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

package com.liferay.adaptive.media.web.internal.servlet;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.exception.AMException;
import com.liferay.adaptive.media.handler.AMRequestHandler;
import com.liferay.adaptive.media.web.internal.constants.AMWebConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.adaptive.media.web.internal.servlet.AMServlet",
		"osgi.http.whiteboard.servlet.pattern=/" + AMWebConstants.SERVLET_PATH + "/*",
		"servlet.init.httpMethods=GET,HEAD"
	},
	service = Servlet.class
)
public class AMServlet extends HttpServlet {

	@Reference(unbind = "-")
	public void setAMRequestHandlerLocator(
		AMRequestHandlerLocator amRequestHandlerLocator) {

		_amRequestHandlerLocator = amRequestHandlerLocator;
	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			AMRequestHandler amRequestHandler =
				_amRequestHandlerLocator.locateForPattern(
					_getRequestHandlerPattern(request));

			if (amRequestHandler == null) {
				response.sendError(
					HttpServletResponse.SC_NOT_FOUND, request.getRequestURI());

				return;
			}

			Optional<AdaptiveMedia<?>> adaptiveMediaOptional =
				amRequestHandler.handleRequest(request);

			AdaptiveMedia<?> adaptiveMedia = adaptiveMediaOptional.orElseThrow(
				AMException.AMNotFound::new);

			Optional<Long> contentLengthOptional =
				adaptiveMedia.getValueOptional(
					AMAttribute.getContentLengthAMAttribute());

			long contentLength = contentLengthOptional.orElse(0L);

			Optional<String> contentTypeOptional =
				adaptiveMedia.getValueOptional(
					AMAttribute.getContentTypeAMAttribute());

			String contentType = contentTypeOptional.orElse(
				ContentTypes.APPLICATION_OCTET_STREAM);

			Optional<String> fileNameOptional = adaptiveMedia.getValueOptional(
				AMAttribute.getFileNameAMAttribute());

			String fileName = fileNameOptional.orElse(null);

			boolean download = ParamUtil.getBoolean(request, "download");

			if (download) {
				ServletResponseUtil.sendFile(
					request, response, fileName, adaptiveMedia.getInputStream(),
					contentLength, contentType,
					HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
			}
			else {
				ServletResponseUtil.sendFile(
					request, response, fileName, adaptiveMedia.getInputStream(),
					contentLength, contentType);
			}
		}
		catch (AMException.AMNotFound amnf) {
			response.sendError(
				HttpServletResponse.SC_NOT_FOUND, request.getRequestURI());
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof PrincipalException) {
				response.sendError(
					HttpServletResponse.SC_FORBIDDEN, request.getRequestURI());
			}
			else {
				response.sendError(
					HttpServletResponse.SC_BAD_REQUEST,
					request.getRequestURI());
			}
		}
	}

	@Override
	protected void doHead(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		doGet(request, response);
	}

	private String _getRequestHandlerPattern(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();

		Matcher matcher = _requestHandlerPattern.matcher(pathInfo);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return StringPool.BLANK;
	}

	private static final Pattern _requestHandlerPattern = Pattern.compile(
		"^/([^/]*)");

	private AMRequestHandlerLocator _amRequestHandlerLocator;

}