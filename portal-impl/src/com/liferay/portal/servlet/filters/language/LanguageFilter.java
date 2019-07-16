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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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

		_portletConfig = PortletConfigFactoryUtil.create(
			portlets.get(0), filterConfig.getServletContext());
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(httpServletResponse);

		processFilter(
			LanguageFilter.class.getName(), httpServletRequest,
			bufferCacheServletResponse, filterChain);

		if (_log.isDebugEnabled()) {
			String completeURL = HttpUtil.getCompleteURL(httpServletRequest);

			_log.debug("Translating response " + completeURL);
		}

		String content = bufferCacheServletResponse.getString();

		content = translateResponse(httpServletRequest, content);

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

	private static final Log _log = LogFactoryUtil.getLog(LanguageFilter.class);

	private PortletConfig _portletConfig;

}