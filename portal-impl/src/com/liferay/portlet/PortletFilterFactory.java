/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.model.PortletApp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.UnavailableException;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletFilterFactory {

	public static PortletFilter create(
			com.liferay.portal.model.PortletFilter portletFilterModel,
			PortletContext ctx)
		throws PortletException {

		return _instance._create(portletFilterModel, ctx);
	}

	public static void destroy(
		com.liferay.portal.model.PortletFilter portletFilterModel) {

		_instance._destroy(portletFilterModel);
	}

	private PortletFilterFactory() {
		_portletFilters =
			new ConcurrentHashMap<String, Map<String, PortletFilter>>();
	}

	private PortletFilter _create(
			com.liferay.portal.model.PortletFilter portletFilterModel,
			PortletContext portletContext)
		throws PortletException {

		PortletApp portletApp = portletFilterModel.getPortletApp();

		Map<String, PortletFilter> portletFilters = _portletFilters.get(
			portletApp.getServletContextName());

		if (portletFilters == null) {
			portletFilters = new ConcurrentHashMap<String, PortletFilter>();

			_portletFilters.put(
				portletApp.getServletContextName(), portletFilters);
		}

		PortletFilter portletFilter = portletFilters.get(
			portletFilterModel.getFilterName());

		if (portletFilter != null) {
			return portletFilter;
		}

		FilterConfig filterConfig = FilterConfigFactory.create(
			portletFilterModel, portletContext);

		if (portletApp.isWARFile()) {
			PortletContextBag portletContextBag = PortletContextBagPool.get(
				portletApp.getServletContextName());

			Map<String, PortletFilter> curPortletFilters =
				portletContextBag.getPortletFilters();

			portletFilter = curPortletFilters.get(
				portletFilterModel.getFilterName());

			portletFilter = _init(
				portletFilterModel, filterConfig, portletFilter);
		}
		else {
			portletFilter = _init(portletFilterModel, filterConfig);
		}

		portletFilters.put(portletFilterModel.getFilterName(), portletFilter);

		return portletFilter;
	}

	private void _destroy(
		com.liferay.portal.model.PortletFilter portletFilterModel) {

		PortletApp portletApp = portletFilterModel.getPortletApp();

		Map<String, PortletFilter> portletFilters = _portletFilters.get(
			portletApp.getServletContextName());

		if (portletFilters == null) {
			return;
		}

		PortletFilter portletFilter = portletFilters.get(
			portletFilterModel.getFilterName());

		if (portletFilter == null) {
			return;
		}

		portletFilter.destroy();

		portletFilters.remove(portletFilterModel.getFilterName());

		FilterConfigFactory.destroy(portletFilterModel);
	}

	private PortletFilter _init(
			com.liferay.portal.model.PortletFilter portletFilterModel,
			FilterConfig filterConfig)
		throws PortletException {

		return _init(portletFilterModel, filterConfig, null);
	}

	private PortletFilter _init(
			com.liferay.portal.model.PortletFilter portletFilterModel,
			FilterConfig filterConfig, PortletFilter portletFilter)
		throws PortletException {

		try {
			if (portletFilter == null) {
				portletFilter = (PortletFilter)InstanceFactory.newInstance(
					portletFilterModel.getFilterClass());
			}

			portletFilter.init(filterConfig);
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new UnavailableException(e.getMessage());
		}

		return portletFilter;
	}

	private static PortletFilterFactory _instance = new PortletFilterFactory();

	private Map<String, Map<String, PortletFilter>> _portletFilters;

}