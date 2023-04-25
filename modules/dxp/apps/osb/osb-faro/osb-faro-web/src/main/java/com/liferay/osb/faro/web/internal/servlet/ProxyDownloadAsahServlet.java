/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(
	property = {
		"osgi.http.whiteboard.context.path=/proxy/download",
		"osgi.http.whiteboard.servlet.name=com.liferay.osb.faro.web.internal.servlet.ProxyDownloadServlet",
		"osgi.http.whiteboard.servlet.pattern=/proxy/download/data-control-tasks/*",
		"osgi.http.whiteboard.servlet.pattern=/proxy/download/suppressions/logs"
	},
	service = Servlet.class
)
public class ProxyDownloadAsahServlet extends BaseAsahServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			URI uri = buildURI(
				httpServletRequest,
				StringUtil.removeSubstring(
					httpServletRequest.getRequestURI(), "/o/proxy/download"));

			URL url = uri.toURL();

			URLConnection urlConnection = url.openConnection();

			urlConnection.setRequestProperty(
				ASAH_PROJECT_ID_HEADER, getProjectId());
			urlConnection.setRequestProperty(
				ASAH_SECURITY_SIGNATURE_HEADER, getSecuritySignature(uri));

			urlConnection.connect();

			httpServletResponse.setContentLength(
				urlConnection.getContentLength());
			httpServletResponse.setContentType(urlConnection.getContentType());
			httpServletResponse.setHeader(
				HttpHeaders.CONTENT_DISPOSITION,
				urlConnection.getHeaderField(HttpHeaders.CONTENT_DISPOSITION));

			ServletResponseUtil.write(
				httpServletResponse, urlConnection.getInputStream());
		}
		catch (URISyntaxException uriSyntaxException) {
			if (_log.isDebugEnabled()) {
				_log.debug(uriSyntaxException);
			}
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			URI uri = buildURI(
				httpServletRequest,
				StringUtil.removeSubstring(
					httpServletRequest.getRequestURI(), "/o/proxy/download"));

			URL url = uri.toURL();

			URLConnection urlConnection = url.openConnection();

			urlConnection.setRequestProperty(
				HttpHeaders.CONTENT_TYPE,
				httpServletRequest.getHeader(HttpHeaders.CONTENT_TYPE));
			urlConnection.setRequestProperty(
				ASAH_PROJECT_ID_HEADER, getProjectId());
			urlConnection.setRequestProperty(
				ASAH_SECURITY_SIGNATURE_HEADER, getSecuritySignature(uri));

			urlConnection.setDoOutput(true);

			try (OutputStream outputStream = urlConnection.getOutputStream();
				InputStream inputStream = httpServletRequest.getInputStream()) {

				StreamUtil.transfer(inputStream, outputStream);
			}

			ServletResponseUtil.write(
				httpServletResponse, urlConnection.getInputStream());
		}
		catch (URISyntaxException uriSyntaxException) {
			if (_log.isDebugEnabled()) {
				_log.debug(uriSyntaxException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProxyDownloadAsahServlet.class);

}