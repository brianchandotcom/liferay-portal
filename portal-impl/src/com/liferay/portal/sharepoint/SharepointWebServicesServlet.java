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

package com.liferay.portal.sharepoint;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bruno Farache
 */
public class SharepointWebServicesServlet extends HttpServlet {

	@Override
	protected void doPost(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					httpServletRequest.getHeader(HttpHeaders.USER_AGENT), " ",
					httpServletRequest.getMethod(), " ",
					httpServletRequest.getRequestURI()));
		}

		try {
			String uri = httpServletRequest.getRequestURI();

			if (uri.equals("/_vti_bin/webs.asmx")) {
				vtiBinWebsAsmx(httpServletRequest, httpServletResponse);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	protected void vtiBinWebsAsmx(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		StringBundler sb = new StringBundler(12);

		String url = StringBundler.concat(
			"http://", httpServletRequest.getLocalAddr(), ":",
			httpServletRequest.getServerPort(), "/sharepoint");

		sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"");
		sb.append("http://schemas.xmlsoap.org/soap/envelope/\">");
		sb.append("<SOAP-ENV:Header/>");
		sb.append("<SOAP-ENV:Body>");
		sb.append("<WebUrlFromPageUrlResponse xmlns=\"");
		sb.append("http://schemas.microsoft.com/sharepoint/soap/\">");
		sb.append("<WebUrlFromPageUrlResult>");
		sb.append(url);
		sb.append("</WebUrlFromPageUrlResult>");
		sb.append("</WebUrlFromPageUrlResponse>");
		sb.append("</SOAP-ENV:Body>");
		sb.append("</SOAP-ENV:Envelope>");

		httpServletResponse.setContentType(ContentTypes.TEXT_XML_UTF8);

		ServletResponseUtil.write(httpServletResponse, sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharepointWebServicesServlet.class);

}