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
import org.xml.sax.SAXException;
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
	public void characters(char[] ch, int start, int length)
		throws SAXException {

		if (_stack.empty()) {
			return;
		}

		StringBuilder stringBuilder = _stack.peek();

		stringBuilder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
		throws SAXException {

		if (qName.equals("async-supported")) {
			boolean asyncSupported = GetterUtil.getBoolean(
				_stack.pop().toString());

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
			String dispatcher = _stack.pop().toString();

			_filterMapping.dispatchers.add(dispatcher.trim().toUpperCase());
		}
		else if (qName.equals("filter")) {
			_webXMLDefinition.setFilterDefinition(
				_filterDefinition.getName(), _filterDefinition);

			_filterDefinition = null;
		}
		else if (qName.equals("filter-class")) {
			String filterClass = _stack.pop().toString();

			Filter filter = _getFilterInstance(filterClass.trim());

			_filterDefinition.setFilter(filter);
		}
		else if (qName.equals("filter-mapping")) {
			FilterDefinition filterDefinition =
				_webXMLDefinition.getFilterDefinitions().get(
					_filterMapping.filterName);

			filterDefinition.setDispatchers(_filterMapping.dispatchers);

			if (_filterMapping.servletName != null) {
				filterDefinition.getServletNames().add(
					_filterMapping.servletName);
			}

			filterDefinition.setURLPatterns(_filterMapping.urlPatterns);

			_filterMapping = null;
		}
		else if (qName.equals("filter-name")) {
			if (_filterMapping != null) {
				String filterName = _stack.pop().toString();

				_filterMapping.filterName = filterName.trim();
			}
			else if (_filterDefinition != null) {
				String filterName = _stack.pop().toString();

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
			_webXMLDefinition.addListenerDefinition(_listenerDefinition);

			_listenerDefinition = null;
		}
		else if (qName.equals("listener-class")) {
			String listenerClass = _stack.pop().toString();

			EventListener eventListener = _getListenerInstance(listenerClass);

			_listenerDefinition.setEventListener(eventListener);
		}
		else if (qName.equals("param-name")) {
			_paramName = _stack.pop().toString().trim();
		}
		else if (qName.equals("param-value")) {
			_paramValue = _stack.pop().toString().trim();
		}
		else if (qName.equals("servlet")) {
			_webXMLDefinition.setServletDefinition(
				_servletDefinition.getName(), _servletDefinition);

			_servletDefinition = null;
		}
		else if (qName.equals("servlet-class")) {
			String servletClass = _stack.pop().toString();

			Servlet servlet = _getServletInstance(servletClass.trim());

			_servletDefinition.setServlet(servlet);
		}
		else if (qName.equals("servlet-mapping")) {
			ServletDefinition servletDefinition =
				_webXMLDefinition.getServletDefinitions().get(
					_servletMapping.servletName);

			servletDefinition.setURLPatterns(_servletMapping.urlPatterns);

			_servletMapping = null;
		}
		else if (qName.equals("servlet-name")) {
			if (_filterMapping != null) {
				String servletName = _stack.pop().toString();

				_filterMapping.servletName = servletName.trim();
			}
			else if (_servletDefinition != null) {
				String servletName = _stack.pop().toString();

				_servletDefinition.setName(servletName.trim());
			}
			else if (_servletMapping != null) {
				String servletName = _stack.pop().toString();

				_servletMapping.servletName = servletName.trim();
			}
		}
		else if (qName.equals("taglib")) {
			_jspConfig.mappings.put(_taglibUri, _taglibLocation);

			_taglibUri = null;
			_taglibLocation = null;
		}
		else if (qName.equals("taglib-location")) {
			_taglibLocation = _stack.pop().toString().trim();
		}
		else if (qName.equals("taglib-uri")) {
			_taglibUri = _stack.pop().toString().trim();
		}
		else if (qName.equals("url-pattern")) {
			if (_filterMapping != null) {
				String urlPattern = _stack.pop().toString();

				_filterMapping.urlPatterns.add(urlPattern.trim());
			}
			else if (_servletMapping != null) {
				String urlPattern = _stack.pop().toString();

				_servletMapping.urlPatterns.add(urlPattern.trim());
			}
		}
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
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
		}

		return _webXMLDefinition;
	}

	@Override
	public void startElement(
			String uri, String localName, String qName, Attributes attributes)
		throws SAXException {

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
				Logger.LOG_ERROR, "Unable to load filter " + filterClassName);

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
				"Unable to load listener " + listenerClassName);

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
				Logger.LOG_ERROR, "Unable to load servlet " + servletClassName,
				e);

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

	private class FilterMapping {

		protected List<String> dispatchers = new ArrayList<>();
		protected String filterName;
		protected String servletName;
		protected List<String> urlPatterns = new ArrayList<>();

	}

	private class JSPConfig {

		protected Map<String, String> mappings = new HashMap<>();

	}

	private class ServletMapping {

		protected String servletName;
		protected List<String> urlPatterns = new ArrayList<>();

	}

}