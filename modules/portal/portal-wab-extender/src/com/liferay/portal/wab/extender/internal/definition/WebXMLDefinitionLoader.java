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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.wab.extender.internal.WabResourceServlet;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import org.osgi.framework.Bundle;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WebXMLDefinitionLoader {

	public WebXMLDefinitionLoader() throws DocumentException {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Document document = SAXReaderUtil.read(
			classLoader.getResource(
				"com/liferay/portal/wab/extender/internal/dependencies" +
					"/default-web.xml"));

		_defaultWebXmlRootElement = document.getRootElement();
	}

	public WebXMLDefinition loadWebXML(Bundle bundle)
		throws DocumentException, IllegalAccessException,
			   InstantiationException {

		WebXMLDefinition webXML = new WebXMLDefinition();

		readContextParameters(bundle, _defaultWebXmlRootElement, webXML);
		readFilters(bundle, _defaultWebXmlRootElement, webXML);
		readListeners(bundle, _defaultWebXmlRootElement, webXML);
		readServlets(bundle, _defaultWebXmlRootElement, webXML);

		URL url = bundle.getEntry("WEB-INF/web.xml");

		if (url != null) {
			Document document = SAXReaderUtil.read(url);

			Element rootElement = document.getRootElement();

			readContextParameters(bundle, rootElement, webXML);
			readFilters(bundle, rootElement, webXML);
			readListeners(bundle, rootElement, webXML);
			readServlets(bundle, rootElement, webXML);
		}

		return webXML;
	}

	protected List<String> getDispatchers(Element element) {
		List<String> dispatchers = new ArrayList<String>();

		for (Element dispatcherElement : element.elements("dispatcher")) {
			dispatchers.add(
				StringUtil.toUpperCase(dispatcherElement.getTextTrim()));
		}

		return dispatchers;
	}

	protected Map<String, String> getErrorPages(List<Element> elements) {
		Map<String, String> errorPages = new HashMap<String, String>();

		for (Element element : elements) {
			String errorCode = element.elementText("error-code");
			String exceptionType = element.elementTextTrim("exception-type");
			String location = element.elementTextTrim("location");

			if (Validator.isNotNull(errorCode)) {
				errorPages.put(location, errorCode);
			}
			else if (Validator.isNotNull(exceptionType)) {
				errorPages.put(location, exceptionType);
			}
		}

		return errorPages;
	}

	protected List<String> getServletNames(Element element) {
		List<String> servletNames = new ArrayList<String>();

		for (Element servletNameElement : element.elements("servlet-name")) {
			servletNames.add(servletNameElement.getTextTrim());
		}

		return servletNames;
	}

	protected List<String> getURLPatterns(Element element) {
		List<String> urlPatterns = new ArrayList<String>();

		for (Element urlPatternElement : element.elements("url-pattern")) {
			String urlPattern = urlPatternElement.getTextTrim();

			urlPatterns.add(urlPattern);
		}

		return urlPatterns;
	}

	protected void readContextParameters(
		Bundle bundle, Element element, WebXMLDefinition webXML) {

		for (Element contextParamElement : element.elements("context-param")) {
			String name = contextParamElement.elementText("param-name");
			String value = contextParamElement.elementText("param-value");

			webXML.setContextParameter(name, value);
		}
	}

	protected void readFilters(
			Bundle bundle, Element element, WebXMLDefinition webXML)
		throws IllegalAccessException, InstantiationException {

		List<Element> filterMappingElements = element.elements(
			"filter-mapping");

		Collections.reverse(new ArrayList<Element>(filterMappingElements));

		for (Element filterElement : element.elements("filter")) {
			String filterClassName = filterElement.elementText("filter-class");

			Class<?> clazz = null;

			try {
				clazz = bundle.loadClass(filterClassName);
			}
			catch (Exception e) {
				_log.error("Unable to load filter " + filterClassName);

				continue;
			}

			FilterDefinition filterDefinition = new FilterDefinition();

			boolean asyncSupported = GetterUtil.getBoolean(
				filterElement.elementText("async-supported"));

			filterDefinition.setAsyncSupported(asyncSupported);

			Filter filter = (Filter)clazz.newInstance();

			filterDefinition.setFilter(filter);

			String filterName = filterElement.elementText("filter-name");

			filterDefinition.setName(filterName);

			List<Element> initParamElements = filterElement.elements(
				"init-param");

			for (Element initParamElement : initParamElements) {
				String paramName = initParamElement.elementText("param-name");
				String paramValue = initParamElement.elementText("param-value");

				filterDefinition.setInitParameter(paramName, paramValue);
			}

			for (int i = 0; i < filterMappingElements.size(); i++) {
				Element filterMappingElement = filterMappingElements.get(i);

				String filterMappingElementFilterName =
					filterMappingElement.elementText("filter-name");

				if (filterMappingElementFilterName.equals(filterName)) {
					List<String> dispatchers = getDispatchers(
						filterMappingElement);

					filterDefinition.setDispatchers(dispatchers);

					filterDefinition.setPriority(i);

					List<String> servletNames = getServletNames(
						filterMappingElement);

					filterDefinition.setServletNames(servletNames);

					List<String> urlPatterns = getURLPatterns(
						filterMappingElement);

					filterDefinition.setURLPatterns(urlPatterns);
				}
			}

			webXML.setFilterDefinition(filterName, filterDefinition);
		}
	}

	protected void readListeners(
			Bundle bundle, Element element, WebXMLDefinition webXML)
		throws IllegalAccessException, InstantiationException {

		for (Element listenerElement : element.elements("listener")) {
			ListenerDefinition listenerDefinition = new ListenerDefinition();

			String listenerClassName = listenerElement.elementText(
				"listener-class");

			Class<? extends EventListener> eventListenerClass = null;

			try {
				Class<?> clazz = bundle.loadClass(listenerClassName);

				eventListenerClass = clazz.asSubclass(EventListener.class);
			}
			catch (Exception e) {
				_log.error("Unable to load listener " + listenerClassName);

				continue;
			}

			EventListener eventListener = eventListenerClass.newInstance();

			listenerDefinition.setEventListener(eventListener);

			webXML.addListenerDefinition(listenerDefinition);
		}
	}

	protected void readServlets(
			Bundle bundle, Element element, WebXMLDefinition webXML)
		throws IllegalAccessException, InstantiationException {

		List<Element> errorPageElements = element.elements("error-page");

		Map<String, String> errorPageLocationMappings = getErrorPages(
			errorPageElements);

		for (Element servletElement : element.elements("servlet")) {
			String servletClassName = servletElement.elementText(
				"servlet-class");

			Class<?> servletClass = null;

			try {
				if (servletClassName.equals(
						WabResourceServlet.class.getName())) {

					servletClass = WabResourceServlet.class;
				}
				else {
					servletClass = bundle.loadClass(servletClassName);
				}
			}
			catch (Exception e) {
				_log.error("Unable to load servlet " + servletClassName);

				continue;
			}

			ServletDefinition servletDefinition = new ServletDefinition();

			boolean asyncSupported = GetterUtil.getBoolean(
				servletElement.elementText("async-supported"));

			servletDefinition.setAsyncSupported(asyncSupported);

			String servletName = servletElement.elementText("servlet-name");

			servletDefinition.setName(servletName);

			Servlet servlet = (Servlet)servletClass.newInstance();

			servletDefinition.setServlet(servlet);

			List<Element> initParamElements = servletElement.elements(
				"init-param");

			for (Element initParamElement : initParamElements) {
				String paramName = initParamElement.elementText("param-name");
				String paramValue = initParamElement.elementText("param-value");

				servletDefinition.setInitParameter(paramName, paramValue);
			}

			List<String> errorPages = new ArrayList<String>();

			for (Element servletMappingElement :
					element.elements("servlet-mapping")) {

				String servletMappingElementServletName =
					servletMappingElement.elementText("servlet-name");

				if (servletMappingElementServletName.equals(servletName)) {
					List<String> urlPatterns = getURLPatterns(
						servletMappingElement);

					for (String urlPattern : urlPatterns) {
						servletDefinition.addURLPattern(urlPattern);

						String errorPage = errorPageLocationMappings.get(
							urlPattern);

						if (errorPage != null) {
							errorPages.add(errorPage);
						}
					}
				}
			}

			servletDefinition.setErrorPages(errorPages);

			webXML.setServletDefinition(servletName, servletDefinition);
		}
	}

	private static final String _SLASH_STAR = "/*";

	private static Log _log = LogFactoryUtil.getLog(
		WebXMLDefinitionLoader.class);

	private Element _defaultWebXmlRootElement;

}