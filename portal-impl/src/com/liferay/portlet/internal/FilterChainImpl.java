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

package com.liferay.portlet.internal;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventPortlet;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderPortlet;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.HeaderFilter;
import javax.portlet.filter.HeaderFilterChain;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class FilterChainImpl implements FilterChain, HeaderFilterChain {

	public FilterChainImpl(
		Portlet portlet, List<? extends PortletFilter> portletFilters) {

		_portlet = portlet;

		if (portletFilters != null) {
			_portletFilters = portletFilters;
		}
		else {
			_portletFilters = Collections.emptyList();
		}
	}

	@Override
	public void doFilter(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		if (_portletFilters.size() > _pos) {
			ActionFilter actionFilter = (ActionFilter)_portletFilters.get(
				_pos++);

			actionFilter.doFilter(actionRequest, actionResponse, this);
		}
		else {
			_portlet.processAction(actionRequest, actionResponse);
		}
	}

	@Override
	public void doFilter(EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		if (_portletFilters.size() > _pos) {
			EventFilter eventFilter = (EventFilter)_portletFilters.get(_pos++);

			eventFilter.doFilter(eventRequest, eventResponse, this);
		}
		else {
			EventPortlet eventPortlet = (EventPortlet)_portlet;

			eventPortlet.processEvent(eventRequest, eventResponse);
		}
	}

	@Override
	public void doFilter(
			HeaderRequest headerRequest, HeaderResponse headerResponse)
		throws IOException, PortletException {

		if (_portletFilters.size() > _pos) {
			HeaderFilter headerFilter = (HeaderFilter)_portletFilters.get(
				_pos++);

			headerFilter.doFilter(headerRequest, headerResponse, this);
		}
		else {
			HeaderPortlet headerPortlet = (HeaderPortlet)_portlet;

			headerPortlet.renderHeaders(headerRequest, headerResponse);
		}
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (_portletFilters.size() > _pos) {
			RenderFilter renderFilter = (RenderFilter)_portletFilters.get(
				_pos++);

			renderFilter.doFilter(renderRequest, renderResponse, this);
		}
		else {
			_portlet.render(renderRequest, renderResponse);
		}
	}

	@Override
	public void doFilter(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		if (_portletFilters.size() > _pos) {
			ResourceFilter resourceFilter = (ResourceFilter)_portletFilters.get(
				_pos++);

			resourceFilter.doFilter(resourceRequest, resourceResponse, this);
		}
		else {
			ResourceServingPortlet resourceServingPortlet =
				(ResourceServingPortlet)_portlet;

			resourceServingPortlet.serveResource(
				resourceRequest, resourceResponse);
		}
	}

	private final Portlet _portlet;
	private final List<? extends PortletFilter> _portletFilters;
	private int _pos;

}