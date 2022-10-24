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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=Layout SEO Dynamic Rendering Filter",
		"url-pattern=/*"
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
				!_isCrawlerUserAgent(
					_layoutSEODynamicRenderingConfiguration.crawlerUserAgents(),
					StringUtil.toLowerCase(
						httpServletRequest.getHeader(
							HttpHeaders.USER_AGENT)))) {

				return false;
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			String requestURI = serviceContext.getCurrentURL();

			for (String ignoredExtension :
					_layoutSEODynamicRenderingConfiguration.
						ignoredExtensions()) {

				if (requestURI.endsWith(ignoredExtension)) {
					return false;
				}
			}

			if (!_isIncludedPath(
					requestURI,
					_layoutSEODynamicRenderingConfiguration.includedPaths())) {

				return false;
			}

			Map<String, String[]> parameterMap =
				httpServletRequest.getParameterMap();

			if (parameterMap.containsKey("_escaped_fragment_")) {
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

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws IOException {

		Http.Options options = new Http.Options();

		Map<String, String> headers = _getHeaders(httpServletRequest);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			options.addHeader(entry.getKey(), entry.getValue());
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		options.setLocation(
			StringBundler.concat(
				_layoutSEODynamicRenderingConfiguration.serviceURL(),
				StringPool.SLASH, serviceContext.getPortalURL(),
				serviceContext.getCurrentURL()));

		options.setNormalizeURI(false);

		_write(_http.URLtoString(options), httpServletResponse);
	}

	private Map<String, String> _getHeaders(
		HttpServletRequest httpServletRequest) {

		Map<String, String> headers = new HashMap<>();

		Enumeration<String> enumeration = httpServletRequest.getHeaderNames();

		while (enumeration.hasMoreElements()) {
			String headerName = enumeration.nextElement();

			if (!_hopByHopHeaderNames.contains(headerName) &&
				!headerName.equals("content-length")) {

				headers.put(
					headerName, httpServletRequest.getHeader(headerName));
			}
		}

		return headers;
	}

	private boolean _isCrawlerUserAgent(
		String[] crawlerUserAgents, String userAgent) {

		for (String crawlerUserAgent : crawlerUserAgents) {
			if (userAgent.contains(StringUtil.toLowerCase(crawlerUserAgent))) {
				return true;
			}
		}

		return false;
	}

	private boolean _isIncludedPath(String requestURI, String[] includedPaths) {
		for (String includedPath : includedPaths) {
			if (requestURI.contains(StringUtil.toLowerCase(includedPath))) {
				return true;
			}
		}

		return false;
	}

	private void _write(String html, HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletResponse.setContentType("text/html; charset=UTF-8");

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write(html);

		printWriter.flush();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEODynamicRenderingFilter.class);

	private final Set<String> _hopByHopHeaderNames = SetUtil.fromArray(
		"connection", "keep-alive", "proxy-authenticate", "proxy-authorization",
		"te", "trailers", "transfer-encoding", "upgrade");

	@Reference
	private Http _http;

	private LayoutSEODynamicRenderingConfiguration
		_layoutSEODynamicRenderingConfiguration;

}