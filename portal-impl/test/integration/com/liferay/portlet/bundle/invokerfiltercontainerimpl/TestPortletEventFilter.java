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

package com.liferay.portlet.bundle.invokerfiltercontainerimpl;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Philip Jones
 * @author Peter Fellwock
 */
@Component(
		immediate = true,
		property = {
			"javax.portlet.name=testPortletFilter",
			"preinitialized.filter=true",
			"service.ranking:Integer=" + Integer.MAX_VALUE
		},
		service = PortletFilter.class
)
public class TestPortletEventFilter implements PortletFilter, EventFilter {

	@Override
	public void destroy() {
		return;
	}

	@Override
	public void doFilter(
		EventRequest arg0, EventResponse arg1, FilterChain arg2) {

		return;
	}

	@Override
	public void init(FilterConfig arg0) throws PortletException {
		return;
	}

}