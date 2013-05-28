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

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.LiferayFilter;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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
		for (Map.Entry<String, Filter> entry : _filters.entrySet()) {
			Filter filter = entry.getValue();

			try {
				filter.destroy();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		_filterConfigs.clear();
		_filterMappings.clear();
		_filters.clear();

		for (InvokerFilter invokerFilter : _invokerFilters) {
			invokerFilter.clearFilterChainsCache();
		}
	}

	public Filter getFilter(String filterName) {
		return _filters.get(filterName);
	}

	public FilterConfig getFilterConfig(String filterName) {
		return _filterConfigs.get(filterName);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			ServletContext servletContext = filterConfig.getServletContext();

			readLiferayFilterWebXML(servletContext, "/WEB-INF/liferay-web.xml");
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new ServletException(e);
		}
	}

	public Filter registerFilter(String filterName, Filter filter) {
		Filter previousFilter = _filters.put(filterName, filter);

		if (previousFilter != null) {
			for (FilterMapping filterMapping : _filterMappings) {
				if (filterMapping.getFilter() == previousFilter) {
					if (filter != null) {
						filterMapping.setFilter(filter);
					}
					else {
						_filterMappings.remove(filterMapping);
						_filterConfigs.remove(filterName);
					}
				}
			}
		}

		for (InvokerFilter invokerFilter : _invokerFilters) {
			invokerFilter.clearFilterChainsCache();
		}

		return previousFilter;
	}

	public void registerFilterMapping(
		FilterMapping filterMapping, String filterName, boolean after) {

		int i = 0;

		if (Validator.isNotNull(filterName)) {
			Filter filter = _filters.get(filterName);

			if (filter != null) {
				for (; i < _filterMappings.size(); i++) {
					FilterMapping currentFilterMapping = _filterMappings.get(i);

					if (currentFilterMapping.getFilter() == filter) {
						break;
					}
				}
			}
		}

		if (after) {
			i++;
		}

		_filterMappings.add(i, filterMapping);

		for (InvokerFilter invokerFilter : _invokerFilters) {
			invokerFilter.clearFilterChainsCache();
		}
	}

	public void unregisterFilterMapping(FilterMapping filterMapping) {
		_filterMappings.remove(filterMapping);

		for (InvokerFilter invokerFilter : _invokerFilters) {
			invokerFilter.clearFilterChainsCache();
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

		for (FilterMapping filterMapping : _filterMappings) {
			if (filterMapping.isMatch(request, dispatcher, uri)) {
				Filter filter = filterMapping.getFilter();

				invokerFilterChain.addFilter(filter);
			}
		}

		return invokerFilterChain;
	}

	protected Filter getFilter(
		ServletContext servletContext, String filterClassName,
		FilterConfig filterConfig) {

		ClassLoader pluginClassLoader = getPluginClassLoader(servletContext);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != pluginClassLoader) {
				currentThread.setContextClassLoader(pluginClassLoader);
			}

			Filter filter = (Filter)InstanceFactory.newInstance(
				pluginClassLoader, filterClassName);

			filter.init(filterConfig);

			return filter;
		}
		catch (Exception e) {
			_log.error("Unable to initialize filter " + filterClassName, e);
		}
		finally {
			if (contextClassLoader != pluginClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}

		return null;
	}

	protected ClassLoader getPluginClassLoader(ServletContext servletContext) {
		ClassLoader classLoader = (ClassLoader)servletContext.getAttribute(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		if (classLoader != null) {
			return classLoader;
		}

		Thread currentThread = Thread.currentThread();

		return currentThread.getContextClassLoader();
	}

	protected void initFilter(
			ServletContext servletContext, String filterName,
			String filterClassName, Map<String, String> initParameterMap)
		throws Exception {

		FilterConfig filterConfig = new InvokerFilterConfig(
			servletContext, filterName, initParameterMap);

		Filter filter = getFilter(
			servletContext, filterClassName, filterConfig);

		if (filter == null) {
			return;
		}

		boolean filterEnabled = true;

		if (filter instanceof LiferayFilter) {

			// We no longer remove disabled filters because they can be enabled
			// at runtime by a hook. The performance difference is negligible
			// since most filters are assumed to be enabled.

			//LiferayFilter liferayFilter = (LiferayFilter)filter;

			//filterEnabled = liferayFilter.isFilterEnabled();
		}

		if (filterEnabled) {
			_filterConfigs.put(filterName, filterConfig);
			_filters.put(filterName, filter);
		}
		else {
			if (filter instanceof PortalLifecycle) {
				PortalLifecycle portalLifecycle = (PortalLifecycle)filter;

				PortalLifecycleUtil.removeDestroy(portalLifecycle);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Removing disabled filter " + filter.getClass());
			}
		}
	}

	protected void initFilterMapping(
		String filterName, List<String> urlPatterns, List<String> dispatchers) {

		Filter filter = _filters.get(filterName);

		if (filter == null) {
			return;
		}

		FilterConfig filterConfig = _filterConfigs.get(filterName);

		if (filterConfig == null) {
			return;
		}

		FilterMapping filterMapping = new FilterMapping(
			filter, filterConfig, urlPatterns, dispatchers);

		_filterMappings.add(filterMapping);
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

		List<Element> filterElements = rootElement.elements("filter");

		for (Element filterElement : filterElements) {
			String filterName = filterElement.elementText("filter-name");
			String filterClassName = filterElement.elementText("filter-class");

			Map<String, String> initParameterMap =
				new HashMap<String, String>();

			List<Element> initParamElements = filterElement.elements(
				"init-param");

			for (Element initParamElement : initParamElements) {
				String name = initParamElement.elementText("param-name");
				String value = initParamElement.elementText("param-value");

				initParameterMap.put(name, value);
			}

			initFilter(
				servletContext, filterName, filterClassName, initParameterMap);
		}

		List<Element> filterMappingElements = rootElement.elements(
			"filter-mapping");

		for (Element filterMappingElement : filterMappingElements) {
			String filterName = filterMappingElement.elementText("filter-name");

			List<String> urlPatterns = new ArrayList<String>();

			List<Element> urlPatternElements = filterMappingElement.elements(
				"url-pattern");

			for (Element urlPatternElement : urlPatternElements) {
				urlPatterns.add(urlPatternElement.getTextTrim());
			}

			List<String> dispatchers = new ArrayList<String>(4);

			List<Element> dispatcherElements = filterMappingElement.elements(
				"dispatcher");

			for (Element dispatcherElement : dispatcherElements) {
				String dispatcher =
					dispatcherElement.getTextTrim().toUpperCase();

				dispatchers.add(dispatcher);
			}

			initFilterMapping(filterName, urlPatterns, dispatchers);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(InvokerFilterHelper.class);

	private Map<String, FilterConfig> _filterConfigs =
		new HashMap<String, FilterConfig>();
	private List<FilterMapping> _filterMappings =
		new CopyOnWriteArrayList<FilterMapping>();
	private Map<String, Filter> _filters = new HashMap<String, Filter>();
	private List<InvokerFilter> _invokerFilters =
		new ArrayList<InvokerFilter>();

}