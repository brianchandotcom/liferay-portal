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

package com.liferay.portal.wab.extender.internal.definition;

import com.liferay.portal.kernel.util.GetterUtil;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WebXMLDefinitionLoader extends DefaultHandler {

	public WebXMLDefinitionLoader(
		Bundle bundle, SAXParserFactory saxParserFactory, Logger logger) {

		_bundle = bundle;
		_saxParserFactory = saxParserFactory;
		_logger = logger;

		_webXMLDefinition = new WebXMLDefinition();
	}

	@Override
	public void characters(char[] c, int start, int length) {
		if (_stack.empty()) {
			return;
		}

		StringBuilder stringBuilder = _stack.peek();

		stringBuilder.append(c, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (qName.equals("async-supported")) {
			boolean asyncSupported = GetterUtil.getBoolean(_stack.pop());

			if (_filterDefinition != null) {
				_filterDefinition.setAsyncSupported(asyncSupported);
			}
			else if (_servletDefinition != null) {
				_servletDefinition.setAsyncSupported(asyncSupported);
			}
		}
		else if (qName.equals("context-param")) {
			_webXMLDefinition.setContextParameter(_paramName, _paramValue);

			_paramName = null;
			_paramValue = null;
		}
		else if (qName.equals("dispatcher")) {
			String dispatcher = String.valueOf(_stack.pop());

			dispatcher = dispatcher.toUpperCase();
			dispatcher = dispatcher.trim();

			_filterMapping.dispatchers.add(dispatcher);
		}
		else if (qName.equals("filter")) {
			if (_filterDefinition.getFilter() != null) {
				_webXMLDefinition.setFilterDefinition(
					_filterDefinition.getName(), _filterDefinition);
			}

			_filterDefinition = null;
		}
		else if (qName.equals("filter-class")) {
			String filterClassName = String.valueOf(_stack.pop());

			Filter filter = _getFilterInstance(filterClassName.trim());

			_filterDefinition.setFilter(filter);
		}
		else if (qName.equals("filter-mapping")) {
			Map<String, FilterDefinition> filterDefinitions =
				_webXMLDefinition.getFilterDefinitions();

			FilterDefinition filterDefinition = filterDefinitions.get(
				_filterMapping.filterName);

			if (filterDefinition != null) {
				filterDefinition.setDispatchers(_filterMapping.dispatchers);

				if (_filterMapping.servletName != null) {
					List<String> servletNames =
						filterDefinition.getServletNames();

					servletNames.add(_filterMapping.servletName);
				}

				filterDefinition.setURLPatterns(_filterMapping.urlPatterns);
			}

			_filterMapping = null;
		}
		else if (qName.equals("filter-name")) {
			if (_filterMapping != null) {
				String filterName = String.valueOf(_stack.pop());

				_filterMapping.filterName = filterName.trim();
			}
			else if (_filterDefinition != null) {
				String filterName = String.valueOf(_stack.pop());

				_filterDefinition.setName(filterName.trim());
			}
		}
		else if (qName.equals("init-param")) {
			if (_filterDefinition != null) {
				_filterDefinition.setInitParameter(_paramName, _paramValue);
			}
			else if (_servletDefinition != null) {
				_servletDefinition.setInitParameter(_paramName, _paramValue);
			}

			_paramName = null;
			_paramValue = null;
		}
		else if (qName.equals("jsp-config")) {
			_webXMLDefinition.setJspTaglibMappings(_jspConfig.mappings);

			_jspConfig = null;
		}
		else if (qName.equals("listener")) {
			if (_listenerDefinition.getEventListener() != null) {
				_webXMLDefinition.addListenerDefinition(_listenerDefinition);
			}

			_listenerDefinition = null;
		}
		else if (qName.equals("listener-class")) {
			String listenerClassName = String.valueOf(_stack.pop());

			EventListener eventListener = _getListenerInstance(
				listenerClassName);

			_listenerDefinition.setEventListener(eventListener);
		}
		else if (qName.equals("param-name")) {
			_paramName = String.valueOf(_stack.pop());
			_paramName = _paramName.trim();
		}
		else if (qName.equals("param-value")) {
			_paramValue = String.valueOf(_stack.pop());
			_paramValue = _paramValue.trim();
		}
		else if (qName.equals("servlet")) {
			if (_servletDefinition.getServlet() != null) {
				_webXMLDefinition.setServletDefinition(
					_servletDefinition.getName(), _servletDefinition);
			}

			_servletDefinition = null;
		}
		else if (qName.equals("servlet-class")) {
			String servletClassName = String.valueOf(_stack.pop());

			Servlet servlet = _getServletInstance(servletClassName.trim());

			_servletDefinition.setServlet(servlet);
		}
		else if (qName.equals("servlet-mapping")) {
			Map<String, ServletDefinition> servletDefinitions =
				_webXMLDefinition.getServletDefinitions();

			ServletDefinition servletDefinition = servletDefinitions.get(
				_servletMapping.servletName);

			if (servletDefinition != null) {
				servletDefinition.setURLPatterns(_servletMapping.urlPatterns);
			}

			_servletMapping = null;
		}
		else if (qName.equals("servlet-name")) {
			if (_filterMapping != null) {
				String servletName = String.valueOf(_stack.pop());

				_filterMapping.servletName = servletName.trim();
			}
			else if (_servletDefinition != null) {
				String servletName = String.valueOf(_stack.pop());

				_servletDefinition.setName(servletName.trim());
			}
			else if (_servletMapping != null) {
				String servletName = String.valueOf(_stack.pop());

				_servletMapping.servletName = servletName.trim();
			}
		}
		else if (qName.equals("taglib")) {
			_jspConfig.mappings.put(_taglibUri, _taglibLocation);

			_taglibUri = null;
			_taglibLocation = null;
		}
		else if (qName.equals("taglib-location")) {
			_taglibLocation = String.valueOf(_stack.pop());
		}
		else if (qName.equals("taglib-uri")) {
			_taglibUri = String.valueOf(_stack.pop());
		}
		else if (qName.equals("url-pattern")) {
			if (_filterMapping != null) {
				String urlPattern = String.valueOf(_stack.pop());

				_filterMapping.urlPatterns.add(urlPattern.trim());
			}
			else if (_servletMapping != null) {
				String urlPattern = String.valueOf(_stack.pop());

				_servletMapping.urlPatterns.add(urlPattern.trim());
			}
		}
	}

	@Override
	public void error(SAXParseException e) {
		_logger.log(Logger.LOG_ERROR, _bundle + ": " + e.getMessage(), e);
	}

	public WebXMLDefinition loadWebXML() throws Exception {
		URL url = _bundle.getEntry("WEB-INF/web.xml");

		if (url != null) {
			try (InputStream inputStream = url.openStream()) {
				SAXParser saxParser = _saxParserFactory.newSAXParser();

				XMLReader xmlReader = saxParser.getXMLReader();

				xmlReader.setContentHandler(this);

				xmlReader.parse(new InputSource(inputStream));
			}
			catch (SAXParseException saxpe) {
				String message = saxpe.getMessage();

				if (message.contains("DOCTYPE is disallowed")) {
					throw new Exception(
						"WEB-INF/web.xml must be updated to the Servlet 2.4 " +
							"specification");
				}

				throw saxpe;
			}
		}

		return _webXMLDefinition;
	}

	@Override
	public void startElement(
		String uri, String localName, String qName, Attributes attributes) {

		if (qName.equals("filter")) {
			_filterDefinition = new FilterDefinition();
		}
		else if (qName.equals("filter-mapping")) {
			_filterMapping = new FilterMapping();
		}
		else if (qName.equals("jsp-config")) {
			_jspConfig = new JSPConfig();
		}
		else if (qName.equals("listener")) {
			_listenerDefinition = new ListenerDefinition();
		}
		else if (qName.equals("servlet")) {
			_servletDefinition = new ServletDefinition();
		}
		else if (qName.equals("servlet-mapping")) {
			_servletMapping = new ServletMapping();
		}
		else if (Arrays.binarySearch(_LEAVES, qName) > -1) {
			_stack.push(new StringBuilder());
		}
	}

	private Filter _getFilterInstance(String filterClassName) {
		try {
			Class<?> clazz = _bundle.loadClass(filterClassName);

			Class<? extends Filter> filterClass = clazz.asSubclass(
				Filter.class);

			return filterClass.newInstance();
		}
		catch (Exception e) {
			_logger.log(
				Logger.LOG_ERROR,
				"Bundle " + _bundle + " is unable to load filter " +
					filterClassName);

			return null;
		}
	}

	private EventListener _getListenerInstance(String listenerClassName) {
		try {
			Class<?> clazz = _bundle.loadClass(listenerClassName);

			Class<? extends EventListener> eventListenerClass =
				clazz.asSubclass(EventListener.class);

			return eventListenerClass.newInstance();
		}
		catch (Exception e) {
			_logger.log(
				Logger.LOG_ERROR,
				"Bundle " + _bundle + " is unable to load listener " +
					listenerClassName);

			return null;
		}
	}

	private Servlet _getServletInstance(String servletClassName) {
		try {
			Class<?> clazz = _bundle.loadClass(servletClassName);

			Class<? extends Servlet> servletClass = clazz.asSubclass(
				Servlet.class);

			return servletClass.newInstance();
		}
		catch (Exception e) {
			_logger.log(
				Logger.LOG_ERROR,
				_bundle + " unable to load servlet " + servletClassName, e);

			return null;
		}
	}

	private static final String[] _LEAVES = new String[] {
		"async-supported", "dispatcher", "error-code", "exception-type",
		"filter-class", "filter-name", "listener-class", "location",
		"param-name", "param-value", "servlet-class", "servlet-name",
		"taglib-location", "taglib-uri", "url-pattern"
	};

	private final Bundle _bundle;
	private FilterDefinition _filterDefinition;
	private FilterMapping _filterMapping;
	private JSPConfig _jspConfig;
	private ListenerDefinition _listenerDefinition;
	private final Logger _logger;
	private String _paramName;
	private String _paramValue;
	private final SAXParserFactory _saxParserFactory;
	private ServletDefinition _servletDefinition;
	private ServletMapping _servletMapping;
	private final Stack<StringBuilder> _stack = new Stack<>();
	private String _taglibLocation;
	private String _taglibUri;
	private final WebXMLDefinition _webXMLDefinition;

	private static class FilterMapping {

		protected List<String> dispatchers = new ArrayList<>();
		protected String filterName;
		protected String servletName;
		protected List<String> urlPatterns = new ArrayList<>();

	}

	private static class JSPConfig {

		protected Map<String, String> mappings = new HashMap<>();

	}

	private static class ServletMapping {

		protected String servletName;
		protected List<String> urlPatterns = new ArrayList<>();

	}

}