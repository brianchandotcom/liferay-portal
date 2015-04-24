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

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.util.StringPlus;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class InvokerFilterHelper {

	public void destroy() {
		_serviceTracker.close();

		for (List<FilterMapping> filterMappings : _filterMappingsMap.values()) {
			FilterMapping filterMapping = filterMappings.get(0);

			Filter filter = filterMapping.getFilter();

			try {
				filter.destroy();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		_filterMappingsMap.clear();
		_filterNames.clear();

		for (InvokerFilter invokerFilter : _invokerFilters) {
			invokerFilter.clearFilterChainsCache();
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			ServletContext servletContext = filterConfig.getServletContext();

			readLiferayFilterWebXML(servletContext, "/WEB-INF/liferay-web.xml");

			Registry registry = RegistryUtil.getRegistry();

			String servletContextName = GetterUtil.getString(
				servletContext.getServletContextName());

			com.liferay.registry.Filter filter = registry.getFilter(
				"(&(objectClass=" + Filter.class.getName() +
					")(servlet-context-name=" + servletContextName +
						")(servlet-filter-name=*))");

			_serviceTracker = registry.trackServices(
				filter, new FilterServiceTrackerCustomizer());

			_serviceTracker.open();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new ServletException(e);
		}
	}

	public void registerFilterMapping(
		FilterMapping filterMapping, String filterName, boolean after) {

		List<FilterMapping> filterMappings = _filterMappingsMap.get(filterName);

		if (filterMappings == null) {
			filterMappings = new CopyOnWriteArrayList<>();

			List<FilterMapping> previousFilterMapping =
				_filterMappingsMap.putIfAbsent(filterName, filterMappings);

			if (previousFilterMapping != null) {
				filterMappings = previousFilterMapping;
			}

			_filterNames.add(filterName);
		}

		if (after) {
			filterMappings.add(filterMapping);
		}
		else {
			filterMappings.add(0, filterMapping);
		}

		for (InvokerFilter invokerFilter : _invokerFilters) {
			invokerFilter.clearFilterChainsCache();
		}
	}

	public void unregisterFilter(String filterName) {
		List<FilterMapping> filterMappings = _filterMappingsMap.remove(
			filterName);

		if (filterMappings == null) {
			return;
		}

		FilterMapping filterMapping = filterMappings.get(0);

		Filter filter = filterMapping.getFilter();

		if (filter != null) {
			try {
				filter.destroy();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		_filterNames.remove(filterName);

		for (InvokerFilter invokerFilter : _invokerFilters) {
			invokerFilter.clearFilterChainsCache();
		}
	}

	public void updateFilterMappings(String filterName, Filter filter) {
		List<FilterMapping> filterMappings = _filterMappingsMap.get(filterName);

		if (filterMappings == null) {
			return;
		}

		for (int i = 0; i < filterMappings.size(); i++) {
			FilterMapping filterMapping = filterMappings.get(i);

			filterMappings.set(i, filterMapping.replaceFilter(filter));
		}
	}

	protected void addInvokerFilter(InvokerFilter invokerFilter) {
		_invokerFilters.add(invokerFilter);
	}

	protected InvokerFilterChain createInvokerFilterChain(
		HttpServletRequest request, Dispatcher dispatcher, String uri,
		FilterChain filterChain) {

		InvokerFilterChain invokerFilterChain = new InvokerFilterChain(
			filterChain);

		for (String filterName : _filterNames) {
			List<FilterMapping> filterMappings = _filterMappingsMap.get(
				filterName);

			if (filterMappings == null) {
				continue;
			}

			for (FilterMapping filterMapping : filterMappings) {
				if (filterMapping.isMatch(request, dispatcher, uri)) {
					invokerFilterChain.addFilter(filterMapping.getFilter());
				}
			}
		}

		return invokerFilterChain;
	}

	protected Filter initFilter(
		ServletContext servletContext, String filterClassName,
		String filterName, FilterConfig filterConfig) {

		ClassLoader pluginClassLoader = servletContext.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != pluginClassLoader) {
			currentThread.setContextClassLoader(pluginClassLoader);
		}

		try {
			Filter filter = (Filter)InstanceFactory.newInstance(
				pluginClassLoader, filterClassName);

			filter.init(filterConfig);

			return filter;
		}
		catch (Exception e) {
			_log.error("Unable to initialize filter " + filterClassName, e);

			return null;
		}
		finally {
			if (contextClassLoader != pluginClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected void readLiferayFilterWebXML(
			ServletContext servletContext, String path)
		throws Exception {

		InputStream inputStream = servletContext.getResourceAsStream(path);

		if (inputStream == null) {
			return;
		}

		Document document = SAXReaderUtil.read(inputStream, true);

		Element rootElement = document.getRootElement();

		Map<String, ObjectValuePair<Filter, FilterConfig>>
			filterObjectValuePairs = new HashMap<>();

		for (Element filterElement : rootElement.elements("filter")) {
			String filterName = filterElement.elementText("filter-name");
			String filterClassName = filterElement.elementText("filter-class");

			Map<String, String> initParameterMap = new HashMap<>();

			List<Element> initParamElements = filterElement.elements(
				"init-param");

			for (Element initParamElement : initParamElements) {
				String name = initParamElement.elementText("param-name");
				String value = initParamElement.elementText("param-value");

				initParameterMap.put(name, value);
			}

			FilterConfig filterConfig = new InvokerFilterConfig(
				servletContext, filterName, initParameterMap);

			Filter filter = initFilter(
				servletContext, filterClassName, filterName, filterConfig);

			if (filter != null) {
				filterObjectValuePairs.put(
					filterName, new ObjectValuePair<>(filter, filterConfig));
			}
		}

		List<Element> filterMappingElements = rootElement.elements(
			"filter-mapping");

		for (Element filterMappingElement : filterMappingElements) {
			String filterName = filterMappingElement.elementText("filter-name");

			List<String> urlPatterns = new ArrayList<>();

			List<Element> urlPatternElements = filterMappingElement.elements(
				"url-pattern");

			for (Element urlPatternElement : urlPatternElements) {
				urlPatterns.add(urlPatternElement.getTextTrim());
			}

			List<String> dispatchers = new ArrayList<>(4);

			List<Element> dispatcherElements = filterMappingElement.elements(
				"dispatcher");

			for (Element dispatcherElement : dispatcherElements) {
				String dispatcher = StringUtil.toUpperCase(
					dispatcherElement.getTextTrim());

				dispatchers.add(dispatcher);
			}

			ObjectValuePair<Filter, FilterConfig> filterObjectValuePair =
				filterObjectValuePairs.get(filterName);

			if (filterObjectValuePair == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No filter and filter config exists with filter name " +
							filterName);
				}

				continue;
			}

			FilterMapping filterMapping = new FilterMapping(
					filterObjectValuePair.getKey(),
					filterObjectValuePair.getValue(), urlPatterns, dispatchers,
					filterName);

			List<FilterMapping> filterMappings = _filterMappingsMap.get(
				filterName);

			if (filterMappings == null) {
				filterMappings = new CopyOnWriteArrayList<>();

				List<FilterMapping> previousFilterMapping =
					_filterMappingsMap.putIfAbsent(filterName, filterMappings);

				if (previousFilterMapping != null) {
					filterMappings = previousFilterMapping;
				}

				_filterNames.add(filterName);
			}

			filterMappings.add(filterMapping);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InvokerFilterHelper.class);

	private final ConcurrentMap<String, List<FilterMapping>>
		_filterMappingsMap = new ConcurrentHashMap<>();
	private final Set<String> _filterNames = new CopyOnWriteArraySet<>();
	private final List<InvokerFilter> _invokerFilters = new ArrayList<>();
	private ServiceTracker<Filter, FilterMapping> _serviceTracker;

	private class FilterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Filter, FilterMapping> {

		@Override
		public FilterMapping addingService(
			ServiceReference<Filter> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			Filter filter = registry.getService(serviceReference);

			String afterFilter = GetterUtil.getString(
				serviceReference.getProperty("after-filter"));
			String beforeFilter = GetterUtil.getString(
				serviceReference.getProperty("before-filter"));
			List<String> dispatchers = StringPlus.asList(
				serviceReference.getProperty("dispatcher"));
			String servletContextName = GetterUtil.getString(
				serviceReference.getProperty("servlet-context-name"),
				StringPool.BLANK);
			String servletFilterName = GetterUtil.getString(
				serviceReference.getProperty("servlet-filter-name"));
			List<String> urlPatterns = StringPlus.asList(
				serviceReference.getProperty("url-pattern"));

			String positionFilterName = beforeFilter;
			boolean after = false;

			if (Validator.isNotNull(afterFilter)) {
				positionFilterName = afterFilter;
				after = true;
			}

			Map<String, String> initParameterMap = new HashMap<>();

			Map<String, Object> properties = serviceReference.getProperties();

			for (String key : properties.keySet()) {
				if (!key.startsWith("init.param.")) {
					continue;
				}

				String value = GetterUtil.getString(
					serviceReference.getProperty(key));

				initParameterMap.put(key, value);
			}

			ServletContext servletContext = ServletContextPool.get(
				servletContextName);

			FilterConfig filterConfig = new InvokerFilterConfig(
				servletContext, servletFilterName, initParameterMap);

			try {
				filter.init(filterConfig);
			}
			catch (ServletException se) {
				_log.error(se, se);

				registry.ungetService(serviceReference);

				return null;
			}

			updateFilterMappings(servletFilterName, filter);

			FilterMapping filterMapping = new FilterMapping(
				filter, filterConfig, urlPatterns, dispatchers,
				servletFilterName);

			registerFilterMapping(filterMapping, positionFilterName, after);

			return filterMapping;
		}

		@Override
		public void modifiedService(
			ServiceReference<Filter> serviceReference,
			FilterMapping filterMapping) {

			removedService(serviceReference, filterMapping);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<Filter> serviceReference,
			FilterMapping filterMapping) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			unregisterFilter(
				GetterUtil.getString(
					serviceReference.getProperty("servlet-filter-name")));
		}

	}

}