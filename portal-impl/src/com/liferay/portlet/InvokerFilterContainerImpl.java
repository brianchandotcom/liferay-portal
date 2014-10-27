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
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.io.Closeable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
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

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=" + rootPortletId + ")(objectClass=" +
				PortletFilter.class.getName() + "))");

		_serviceTracker =
			registry.trackServices(
				filter,
				new PortletFilterServiceTrackerCustomizer(portletContext));

		_serviceTracker.open();

		PortletApp portletApp = portlet.getPortletApp();

		PortletContextBag portletContextBag = PortletContextBagPool.get(
			portletApp.getServletContextName());

		if (portletApp.isWARFile() && (portletContextBag == null)) {
			return;
		}

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("javax.portlet.name", rootPortletId);
		properties.put("preinitialized.filter", Boolean.TRUE);

		Map<String, com.liferay.portal.model.PortletFilter> portletFilters =
			portlet.getPortletFilters();

		for (Map.Entry<String, com.liferay.portal.model.PortletFilter> entry :
				portletFilters.entrySet()) {

			com.liferay.portal.model.PortletFilter portletFilterModel =
				entry.getValue();

			PortletFilter portletFilter = PortletFilterFactory.create(
				portletFilterModel, portletContext);

			ServiceRegistration<PortletFilter> serviceRegistration =
				registry.registerService(
					PortletFilter.class, portletFilter, properties);

			_serviceRegistrations.add(serviceRegistration);
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

				ServiceRegistration<PortletFilter> serviceRegistration =
					registry.registerService(
						PortletFilter.class, portletFilter, properties);

				_serviceRegistrations.add(serviceRegistration);
			}
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(classLoader);
		}
	}

	@Override
	public void close() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();

		_serviceTracker.close();

		_actionFilters.clear();
		_eventFilters.clear();
		_renderFilters.clear();
		_resourceFilters.clear();
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

	private final List<ActionFilter> _actionFilters =
		new CopyOnWriteArrayList<>();
	private final List<EventFilter> _eventFilters =
		new CopyOnWriteArrayList<>();
	private final List<RenderFilter> _renderFilters =
		new CopyOnWriteArrayList<>();
	private final List<ResourceFilter> _resourceFilters =
		new CopyOnWriteArrayList<>();
	private final List<ServiceRegistration<PortletFilter>>
		_serviceRegistrations = new CopyOnWriteArrayList<>();
	private final ServiceTracker<PortletFilter, PortletFilter> _serviceTracker;

	private class PortletFilterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<PortletFilter, PortletFilter> {

		public PortletFilterServiceTrackerCustomizer(
			PortletContext portletContext) {

			_portletContext = portletContext;
		}

		@Override
		public PortletFilter addingService(
			ServiceReference<PortletFilter> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			PortletFilter portletFilter = registry.getService(serviceReference);

			boolean preinitializedFilter = GetterUtil.getBoolean(
				serviceReference.getProperty("preinitialized.filter"));

			if (!preinitializedFilter) {
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

					registry.ungetService(serviceReference);

					return null;
				}
			}

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

			return portletFilter;
		}

		@Override
		public void modifiedService(
			ServiceReference<PortletFilter> serviceReference,
			PortletFilter portletFilter) {
		}

		@Override
		public void removedService(
			ServiceReference<PortletFilter> serviceReference,
			PortletFilter portletFilter) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_actionFilters.remove(portletFilter);
			_eventFilters.remove(portletFilter);
			_renderFilters.remove(portletFilter);
			_resourceFilters.remove(portletFilter);

			boolean preinitializedFilter = GetterUtil.getBoolean(
				serviceReference.getProperty("preinitialized.filter"));

			if (preinitializedFilter) {
				return;
			}

			portletFilter.destroy();
		}

		private final PortletContext _portletContext;

	}

}