/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.spring.extension.internal;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.annotations.PortletLifecycleFilter;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.HeaderFilter;
import javax.portlet.filter.HeaderFilterChain;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.i18n.SimpleLocaleContext;

/**
 * @author Neil Griffin
 */
@PortletLifecycleFilter(filterName = "springBeanPortletFilter")
public class SpringBeanPortletFilter
	implements ActionFilter, EventFilter, HeaderFilter, RenderFilter,
			   ResourceFilter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			ActionRequest actionRequest, ActionResponse actionResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		_doFilter(
			() -> filterChain.doFilter(actionRequest, actionResponse),
			actionRequest);
	}

	@Override
	public void doFilter(
			EventRequest eventRequest, EventResponse eventResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		_doFilter(
			() -> filterChain.doFilter(eventRequest, eventResponse),
			eventRequest);
	}

	@Override
	public void doFilter(
			HeaderRequest headerRequest, HeaderResponse headerResponse,
			HeaderFilterChain headerFilterChain)
		throws IOException, PortletException {

		_doFilter(
			() -> headerFilterChain.doFilter(headerRequest, headerResponse),
			headerRequest);
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		_doFilter(
			() -> filterChain.doFilter(renderRequest, renderResponse),
			renderRequest);
	}

	@Override
	public void doFilter(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		_doFilter(
			() -> filterChain.doFilter(resourceRequest, resourceResponse),
			resourceRequest);
	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
	}

	private void _doFilter(
			FilterChainRunnable filterChainRunnable,
			PortletRequest portletRequest)
		throws IOException, PortletException {

		LocaleContext localeContext = LocaleContextHolder.getLocaleContext();

		LocaleContextHolder.setLocaleContext(
			new SimpleLocaleContext(portletRequest.getLocale()), false);

		filterChainRunnable.doFilter();

		LocaleContextHolder.setLocaleContext(localeContext, false);
	}

	@FunctionalInterface
	private interface FilterChainRunnable {

		public void doFilter() throws IOException, PortletException;

	}

}