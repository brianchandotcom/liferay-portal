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

package com.liferay.portlet;

import com.liferay.portal.model.PortletApp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletContext;
import javax.portlet.filter.FilterConfig;

/**
 * @author Brian Wing Shun Chan
 */
public class FilterConfigFactory {

	public static FilterConfig create(
		com.liferay.portal.model.PortletFilter portletFilter,
		PortletContext ctx) {

		return _instance._create(portletFilter, ctx);
	}

	public static void destroy(
		com.liferay.portal.model.PortletFilter portletFilter) {

		_instance._destroy(portletFilter);
	}

	private FilterConfigFactory() {
		_pool = new ConcurrentHashMap<String, Map<String, FilterConfig>>();
	}

	private FilterConfig _create(
		com.liferay.portal.model.PortletFilter portletFilter,
		PortletContext ctx) {

		PortletApp portletApp = portletFilter.getPortletApp();

		Map<String, FilterConfig> filterConfigs = _pool.get(
			portletApp.getServletContextName());

		if (filterConfigs == null) {
			filterConfigs = new ConcurrentHashMap<String, FilterConfig>();

			_pool.put(portletApp.getServletContextName(), filterConfigs);
		}

		FilterConfig filterConfig = filterConfigs.get(
			portletFilter.getFilterName());

		if (filterConfig == null) {
			filterConfig = new FilterConfigImpl(
				portletFilter.getFilterName(), ctx,
				portletFilter.getInitParams());

			filterConfigs.put(portletFilter.getFilterName(), filterConfig);
		}

		return filterConfig;
	}

	private void _destroy(
		com.liferay.portal.model.PortletFilter portletFilter) {

		PortletApp portletApp = portletFilter.getPortletApp();

		_pool.remove(portletApp.getServletContextName());
	}

	private static final FilterConfigFactory _instance =
		new FilterConfigFactory();

	private final Map<String, Map<String, FilterConfig>> _pool;

}