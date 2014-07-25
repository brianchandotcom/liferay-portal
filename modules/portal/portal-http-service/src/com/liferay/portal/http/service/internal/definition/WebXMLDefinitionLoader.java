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

package com.liferay.portal.http.service.internal.definition;

import com.liferay.portal.http.service.internal.WabResourceServlet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

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
				"com/liferay/portal/http/service/internal" +
					"/dependencies/default-web.xml"));

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

		for (Element curElement : element.elements("dispatcher")) {
			dispatchers.add(StringUtil.toUpperCase(curElement.getTextTrim()));
		}

		return dispatchers;
	}

	protected Map<String, String> getErrorPageLocationMappings(
		List<Element> elements) {

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

		for (Element curElement : element.elements("servlet-name")) {
			servletNames.add(curElement.getTextTrim());
		}

		return servletNames;
	}

	protected List<String> getURLPatterns(Element element) {
		List<String> urlPatterns = new ArrayList<String>();

		for (Element curElement : element.elements("url-pattern")) {
			String urlPattern = curElement.getTextTrim();

			if (urlPattern.endsWith(_SLASH_STAR) && (urlPattern.length() > 2)) {
				urlPattern = urlPattern.substring(0, urlPattern.length() - 2);
			}

			if (urlPattern.startsWith(StringPool.STAR)) {
				urlPattern = StringPool.SLASH.concat(urlPattern);
			}

			urlPatterns.add(urlPattern);
		}

		return urlPatterns;
	}

	protected void readContextParameters(
		Bundle bundle, Element rootElement, WebXMLDefinition webXML) {

		for (Element element : rootElement.elements("context-param")) {
			String name = element.elementText("param-name");
			String value = element.elementText("param-value");

			webXML.setContextParameter(name, value);
		}
	}

	protected void readFilters(
			Bundle bundle, Element rootElement, WebXMLDefinition webXML)
		throws IllegalAccessException, InstantiationException {

		List<Element> filterElements = rootElement.elements("filter");

		List<Element> filterMappingElements = rootElement.elements(
			"filter-mapping");

		Collections.reverse(new ArrayList<Element>(filterMappingElements));

		for (Element filterElement : filterElements) {
			String filterClassName = filterElement.elementText("filter-class");

			Class<?> clazz = null;

			try {
				clazz = bundle.loadClass(filterClassName);
			}
			catch (Exception e) {
				_log.error("Unable to load filter " + filterClassName);

				continue;
			}

			Filter filter = (Filter)clazz.newInstance();

			FilterDefinition filterDefinition = new FilterDefinition();

			filterDefinition.setFilter(filter);

			String filterName = filterElement.elementText("filter-name");

			filterDefinition.setName(filterName);

			boolean asyncSupported = GetterUtil.getBoolean(
				filterElement.elementText("async-supported"));

			filterDefinition.setAsyncSupported(asyncSupported);

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

					List<String> servletNames = getServletNames(
						filterMappingElement);

					filterDefinition.setServletNames(servletNames);

					List<String> urlPatterns = getURLPatterns(
						filterMappingElement);

					filterDefinition.setURLPatterns(urlPatterns);

					filterDefinition.setPriority(i);
				}
			}

			webXML.setFilterDefinition(filterName, filterDefinition);
		}
	}

	protected void readListeners(
			Bundle bundle, Element rootElement, WebXMLDefinition webXML)
		throws IllegalAccessException, InstantiationException {

		List<Element> listenerElements = rootElement.elements("listener");

		for (Element listenerElement : listenerElements) {
			ListenerDefinition listenerDefinition = new ListenerDefinition();

			String listenerClassName = listenerElement.elementText(
				"listener-class");

			Class<? extends EventListener> clazz = null;

			try {
				clazz = bundle.loadClass(listenerClassName).asSubclass(
					EventListener.class);
			}
			catch (Exception e) {
				_log.error("Unable to load listener " + listenerClassName);

				continue;
			}

			EventListener listener = clazz.newInstance();

			listenerDefinition.setListener(listener);

			webXML.addListenerDefinition(listenerDefinition);
		}
	}

	protected void readServlets(
			Bundle bundle, Element rootElement, WebXMLDefinition webXML)
		throws IllegalAccessException, InstantiationException {

		List<Element> servletElements = rootElement.elements("servlet");

		List<Element> servletMappingElements = rootElement.elements(
			"servlet-mapping");

		List<Element> errorPageElements = rootElement.elements(
			"error-page");

		Map<String, String> errorPageLocationMappings =
			getErrorPageLocationMappings(errorPageElements);

		for (Element servletElement : servletElements) {
			String servletClassName = servletElement.elementText(
				"servlet-class");

			Class<?> servletClass = null;

			try {
				if (WabResourceServlet.class.getName().equals(
						servletClassName)) {

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

			Servlet servlet = (Servlet)servletClass.newInstance();

			ServletDefinition servletDefinition = new ServletDefinition();

			servletDefinition.setServlet(servlet);

			String servletName = servletElement.elementText("servlet-name");

			servletDefinition.setName(servletName);

			boolean asyncSupported = GetterUtil.getBoolean(
				servletElement.elementText("async-supported"));

			servletDefinition.setAsyncSupported(asyncSupported);

			List<Element> initParamElements = servletElement.elements(
				"init-param");

			for (Element initParamElement : initParamElements) {
				String paramName = initParamElement.elementText("param-name");
				String paramValue = initParamElement.elementText("param-value");

				servletDefinition.setInitParameter(paramName, paramValue);
			}

			List<String> errorPages = new ArrayList<String>();

			for (Element servletMappingElement : servletMappingElements) {
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