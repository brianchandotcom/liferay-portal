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

package com.liferay.layout.seo.web.internal.servlet.filter;

import com.liferay.layout.seo.web.internal.configuration.LayoutSEODynamicRenderingConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jamie Sammons
 */
@Component(
	configurationPid = "com.liferay.layout.seo.web.internal.configuration.LayoutSEODynamicRenderingConfiguration",
	immediate = true,
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=SEO Dynamic Rendering Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class LayoutSEODynamicRenderingFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			if (!_layoutSEODynamicRenderingConfiguration.enabled() ||
				!_validateUserAgent(
					StringUtil.toLowerCase(
						httpServletRequest.getHeader(HttpHeaders.USER_AGENT)),
					_layoutSEODynamicRenderingConfiguration.
						crawlerUserAgents())) {

				return false;
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			String requestURI = serviceContext.getCurrentURL();

			for (String extension :
					_layoutSEODynamicRenderingConfiguration.
						extensionIgnoreList()) {

				if (requestURI.endsWith(extension)) {
					return false;
				}
			}

			if (!_validatePath(
					requestURI,
					_layoutSEODynamicRenderingConfiguration.pathList())) {

				return false;
			}

			Map<String, String[]> parameterMap =
				httpServletRequest.getParameterMap();

			if (parameterMap.containsKey(_ESCAPED_FRAGMENT_KEY)) {
				return true;
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return true;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_layoutSEODynamicRenderingConfiguration =
			ConfigurableUtil.createConfigurable(
				LayoutSEODynamicRenderingConfiguration.class, properties);
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws IOException {

		String serviceURL =
			_layoutSEODynamicRenderingConfiguration.serviceUrl();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		String requestURL =
			serviceContext.getPortalURL() + serviceContext.getCurrentURL();

		Http.Options options = new Http.Options();

		options.setNormalizeURI(false);

		Map<String, String> headers = _getRequestHeaders(httpServletRequest);

		for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
			options.addHeader(headerEntry.getKey(), headerEntry.getValue());
		}

		String serviceToken =
			_layoutSEODynamicRenderingConfiguration.serviceToken();

		if (!serviceToken.isEmpty()) {
			options.addHeader("X-Prerender-Token", serviceToken);
		}

		options.setLocation(serviceURL + StringPool.SLASH + requestURL);

		String content = _http.URLtoString(options);

		_getHtml(content, httpServletResponse);
	}

	private List<String> _getHopByHopHeaders() {
		String[] hopByHopHeaderList = {
			"connection", "keep-alive", "proxy-authenticate",
			"proxy-authorization", "te", "trailers", "transfer-encoding",
			"upgrade"
		};

		return Arrays.asList(hopByHopHeaderList);
	}

	private void _getHtml(
		String html, HttpServletResponse httpServletResponse) {

		httpServletResponse.setContentType("text/html; charset=UTF-8");

		try (PrintWriter printWriter = httpServletResponse.getWriter()) {
			printWriter.write(html);

			printWriter.flush();
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}
	}

	private Map<String, String> _getRequestHeaders(
		HttpServletRequest httpServletRequest) {

		HashMap<String, String> headers = new HashMap<>();

		Enumeration<String> enumeration = httpServletRequest.getHeaderNames();

		while (enumeration.hasMoreElements()) {
			String headerName = enumeration.nextElement();

			String value = httpServletRequest.getHeader(headerName);

			if (!_getHopByHopHeaders().contains(headerName) &&
				!headerName.equals("content-length")) {

				headers.put(headerName, value);
			}
		}

		return headers;
	}

	private boolean _validatePath(String renderURI, String[] pathList) {
		boolean validPath = false;

		for (String path : pathList) {
			if (renderURI.contains(StringUtil.toLowerCase(path))) {
				validPath = true;

				break;
			}
		}

		return validPath;
	}

	private boolean _validateUserAgent(
		String requestUserAgent, String[] userAgents) {

		boolean validUserAgent = false;

		for (String userAgent : userAgents) {
			if (requestUserAgent.contains(StringUtil.toLowerCase(userAgent))) {
				validUserAgent = true;

				break;
			}
		}

		return validUserAgent;
	}

	private static final String _ESCAPED_FRAGMENT_KEY = "_escaped_fragment_";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEODynamicRenderingFilter.class);

	@Reference
	private Http _http;

	private LayoutSEODynamicRenderingConfiguration
		_layoutSEODynamicRenderingConfiguration;

}