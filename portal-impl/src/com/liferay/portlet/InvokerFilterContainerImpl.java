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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.impl.PortletFilterImpl;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceTrackerCollections;

import java.io.Closeable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

/**
 * @author Raymond Augé
 */
public class InvokerFilterContainerImpl
	implements Closeable, InvokerFilterContainer {

	public InvokerFilterContainerImpl(
			Portlet portlet, PortletContext portletContext)
		throws PortletException {

		String rootPortletId = portlet.getRootPortletId();

		String filterString = "(javax.portlet.name=" + rootPortletId + ")";

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("javax.portlet.name", rootPortletId);

		_actionFilters = ServiceTrackerCollections.list(
			ActionFilter.class, filterString,
			new PortletFilterServiceTrackerCustomizer<ActionFilter>(
				portletContext),
			properties);
		_eventFilters = ServiceTrackerCollections.list(
			EventFilter.class, filterString,
			new PortletFilterServiceTrackerCustomizer<EventFilter>(
				portletContext),
			properties);
		_renderFilters = ServiceTrackerCollections.list(
			RenderFilter.class, filterString,
			new PortletFilterServiceTrackerCustomizer<RenderFilter>(
				portletContext),
			properties);
		_resourceFilters = ServiceTrackerCollections.list(
			ResourceFilter.class, filterString,
			new PortletFilterServiceTrackerCustomizer<ResourceFilter>(
				portletContext),
			properties);

		PortletApp portletApp = portlet.getPortletApp();

		PortletContextBag portletContextBag = PortletContextBagPool.get(
			portletApp.getServletContextName());

		if (portletApp.isWARFile() && (portletContextBag == null)) {
			return;
		}

		Map<String, com.liferay.portal.model.PortletFilter> portletFilters =
			portlet.getPortletFilters();

		for (Map.Entry<String, com.liferay.portal.model.PortletFilter> entry :
				portletFilters.entrySet()) {

			com.liferay.portal.model.PortletFilter portletFilterModel =
				entry.getValue();

			PortletFilter portletFilter = PortletFilterFactory.create(
				portletFilterModel, portletContext);

			Set<String> lifecycles = portletFilterModel.getLifecycles();

			if (lifecycles.contains(PortletRequest.ACTION_PHASE)) {
				_actionFilters.add((ActionFilter)portletFilter);
			}

			if (lifecycles.contains(PortletRequest.EVENT_PHASE)) {
				_eventFilters.add((EventFilter)portletFilter);
			}

			if (lifecycles.contains(PortletRequest.RENDER_PHASE)) {
				_renderFilters.add((RenderFilter)portletFilter);
			}

			if (lifecycles.contains(PortletRequest.RESOURCE_PHASE)) {
				_resourceFilters.add((ResourceFilter)portletFilter);
			}
		}

		ClassLoader classLoader = ClassLoaderUtil.getContextClassLoader();

		try {
			ClassLoaderUtil.setContextClassLoader(
				ClassLoaderUtil.getPortalClassLoader());

			for (String portletFilterClassName :
					PropsValues.PORTLET_FILTERS_SYSTEM) {

				com.liferay.portal.model.PortletFilter portletFilterModel =
					new PortletFilterImpl(
						portletFilterClassName, portletFilterClassName,
						Collections.<String>emptySet(),
						Collections.<String, String>emptyMap(), portletApp);

				PortletFilter portletFilter = PortletFilterFactory.create(
					portletFilterModel, portletContext);

				_systemPortletFilters.add(portletFilter);

				if (portletFilter instanceof ActionFilter) {
					_actionFilters.add((ActionFilter)portletFilter);
				}

				if (portletFilter instanceof EventFilter) {
					_eventFilters.add((EventFilter)portletFilter);
				}

				if (portletFilter instanceof RenderFilter) {
					_renderFilters.add((RenderFilter)portletFilter);
				}

				if (portletFilter instanceof ResourceFilter) {
					_resourceFilters.add((ResourceFilter)portletFilter);
				}
			}
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(classLoader);
		}
	}

	@Override
	public void close() {
	}

	@Override
	public List<ActionFilter> getActionFilters() {
		return _actionFilters;
	}

	@Override
	public List<EventFilter> getEventFilters() {
		return _eventFilters;
	}

	@Override
	public List<RenderFilter> getRenderFilters() {
		return _renderFilters;
	}

	@Override
	public List<ResourceFilter> getResourceFilters() {
		return _resourceFilters;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InvokerFilterContainerImpl.class);

	private List<ActionFilter> _actionFilters;
	private List<EventFilter> _eventFilters;
	private List<RenderFilter> _renderFilters;
	private List<ResourceFilter> _resourceFilters;

	private class PortletFilterServiceTrackerCustomizer<T extends PortletFilter>
		implements ServiceTrackerCustomizer<T, T> {

		public PortletFilterServiceTrackerCustomizer(
			PortletContext portletContext) {

			_portletContext = portletContext;
		}

		@Override
		public T addingService(ServiceReference<T> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			T portletFilter = registry.getService(serviceReference);

			String filterName = GetterUtil.getString(
				serviceReference.getProperty("service.id"),
				ClassUtil.getClassName(portletFilter));

			Map<String, String> params = new HashMap<String, String>();

			for (String key : serviceReference.getPropertyKeys()) {
				String value = GetterUtil.getString(
					serviceReference.getProperty(key));

				params.put(key, value);
			}

			FilterConfig filterConfig = new FilterConfigImpl(
				filterName, _portletContext, params);

			try {
				portletFilter.init(filterConfig);
			}
			catch (PortletException e) {
				_log.error(e, e);

				return null;
			}

			return portletFilter;
		}

		@Override
		public void modifiedService(
			ServiceReference<T> serviceReference, T portletFilter) {
		}

		@Override
		public void removedService(
			ServiceReference<T> serviceReference, T portletFilter) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			portletFilter.destroy();
		}

		private final PortletContext _portletContext;

	}

}