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

package com.liferay.portal.servlet.filters.language;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletConfig;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 */
public class LanguageFilter extends BasePortalFilter {

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		ServletContext servletContext = filterConfig.getServletContext();

		PortletApp portletApp = (PortletApp)servletContext.getAttribute(
			PortletServlet.PORTLET_APP);

		if ((portletApp == null) || !portletApp.isWARFile()) {
			return;
		}

		List<Portlet> portlets = portletApp.getPortlets();

		if (portlets.size() <= 0) {
			return;
		}

		_eTags.clear();

		_portletConfig = PortletConfigFactoryUtil.create(
			portlets.get(0), filterConfig.getServletContext());
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		String eTagKey = _getETagKey(httpServletRequest);

		String ifNoneMatch = httpServletRequest.getHeader(
			HttpHeaders.IF_NONE_MATCH);

		if ((ifNoneMatch != null) && ifNoneMatch.equals(_eTags.get(eTagKey))) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

			return;
		}

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(httpServletResponse);

		processFilter(
			LanguageFilter.class.getName(),
			new NoCacheHttpServletRequestWrapper(httpServletRequest),
			bufferCacheServletResponse, filterChain);

		if (_log.isDebugEnabled()) {
			String completeURL = HttpComponentsUtil.getCompleteURL(
				httpServletRequest);

			_log.debug("Translating response " + completeURL);
		}

		String content = bufferCacheServletResponse.getString();

		content = translateResponse(httpServletRequest, content);

		httpServletResponse.setHeader(
			HttpHeaders.CACHE_CONTROL, "private, no-cache");

		String eTag =
			StringPool.QUOTE + DigesterUtil.digest("SHA-1", content) +
				StringPool.QUOTE;

		_eTags.put(eTagKey, eTag);

		httpServletResponse.setHeader(HttpHeaders.ETAG, eTag);

		ServletResponseUtil.write(httpServletResponse, content);
	}

	protected String translateResponse(
		HttpServletRequest httpServletRequest, String content) {

		Locale locale = LocaleUtil.fromLanguageId(
			LanguageUtil.getLanguageId(httpServletRequest));

		return LanguageUtil.process(
			() -> {
				ResourceBundle resourceBundle =
					LanguageResources.getResourceBundle(locale);

				if (_portletConfig != null) {
					resourceBundle = new AggregateResourceBundle(
						_portletConfig.getResourceBundle(locale),
						resourceBundle);
				}

				return resourceBundle;
			},
			locale, content);
	}

	private String _getETagKey(HttpServletRequest httpServletRequest) {
		return LanguageUtil.getLanguageId(httpServletRequest) +
			StringPool.POUND + httpServletRequest.getRequestURI();
	}

	private static final Log _log = LogFactoryUtil.getLog(LanguageFilter.class);

	private final ConcurrentHashMap<String, String> _eTags =
		new ConcurrentHashMap<>();
	private PortletConfig _portletConfig;

	private static class NoCacheHttpServletRequestWrapper
		extends HttpServletRequestWrapper {

		public NoCacheHttpServletRequestWrapper(
			HttpServletRequest httpServletRequest) {

			super(httpServletRequest);

			_httpServletRequest = httpServletRequest;
		}

		public long getDateHeader(String name) {
			if (StringUtil.equalsIgnoreCase(name, "If-Modified-Since") ||
				StringUtil.equalsIgnoreCase(name, "If-None-Match")) {

				return -1;
			}

			return _httpServletRequest.getDateHeader(name);
		}

		public String getHeader(String name) {
			if (StringUtil.equalsIgnoreCase(name, "If-Modified-Since") ||
				StringUtil.equalsIgnoreCase(name, "If-None-Match")) {

				return null;
			}

			return _httpServletRequest.getHeader(name);
		}

		public Enumeration<String> getHeaderNames() {
			List<String> headerNames = new ArrayList<>();

			Enumeration<String> headerNamesEnumeration =
				_httpServletRequest.getHeaderNames();

			while (headerNamesEnumeration.hasMoreElements()) {
				String name = headerNamesEnumeration.nextElement();

				if (StringUtil.equalsIgnoreCase(name, "If-Modified-Since") ||
					StringUtil.equalsIgnoreCase(name, "If-None-Match")) {

					continue;
				}

				headerNames.add(headerNamesEnumeration.nextElement());
			}

			return new Enumeration<String>() {

				@Override
				public boolean hasMoreElements() {
					if (_nextIndex < headerNames.size()) {
						return true;
					}

					return false;
				}

				@Override
				public String nextElement() {
					if (!hasMoreElements()) {
						throw new NoSuchElementException();
					}

					_nextIndex++;

					return headerNames.get(_nextIndex - 1);
				}

				private int _nextIndex;

			};
		}

		public Enumeration<String> getHeaders(String name) {
			if (StringUtil.equalsIgnoreCase(name, "If-Modified-Since") ||
				StringUtil.equalsIgnoreCase(name, "If-None-Match")) {

				return null;
			}

			return _httpServletRequest.getHeaders(name);
		}

		public int getIntHeader(String name) {
			if (StringUtil.equalsIgnoreCase(name, "If-Modified-Since") ||
				StringUtil.equalsIgnoreCase(name, "If-None-Match")) {

				return -1;
			}

			return _httpServletRequest.getIntHeader(name);
		}

		private final HttpServletRequest _httpServletRequest;

	}

}