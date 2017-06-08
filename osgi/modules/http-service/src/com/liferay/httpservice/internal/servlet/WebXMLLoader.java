/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.servlet;

import com.liferay.httpservice.internal.definition.FilterDefinition;
import com.liferay.httpservice.internal.definition.ListenerDefinition;
import com.liferay.httpservice.internal.definition.ServletDefinition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.net.URL;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import org.osgi.framework.Bundle;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WebXMLLoader {

	public WebXMLLoader() {
		_defaultWeb = this.getClass().getResource(
			"/com/liferay/httpservice/internal/servlet/dependencies/" +
				"default-web.xml");
	}

	public WebXML loadWebXML(Bundle bundle) {
		WebXML webXML = new WebXML();

		try {
			Document webXmlDocument = SAXReaderUtil.read(_defaultWeb);

			Element root = webXmlDocument.getRootElement();

			loadContext(bundle, root, webXML);
			loadFilters(bundle, root, webXML);
			loadServlets(bundle, root, webXML);

			URL xmlURL = bundle.getEntry("WEB-INF/web.xml");

			if (xmlURL != null) {
				webXmlDocument = SAXReaderUtil.read(xmlURL);

				root = webXmlDocument.getRootElement();

				loadContext(bundle, root, webXML);
				loadFilters(bundle, root, webXML);
				loadServlets(bundle, root, webXML);
			}
		}
		catch (DocumentException de) {
			_log.error(de);
		}

		return webXML;
	}

	protected static void loadContext(
		Bundle bundle, Element root, WebXML webXML) {

		Hashtable<String, String> contextParams = webXML.getContextParams();

		for (Element element : root.elements("context-param")) {
			String contextParamName = element.element(
				"param-name").getTextTrim();
			String contextParamValue = element.element(
				"param-value").getTextTrim();

			webXML.setContextParam(contextParamName, contextParamValue);
		}

		List<ListenerDefinition> listeners = webXML.getListeners();

		for (Element listenerElement : root.elements("listener")) {
			String listenerClass = listenerElement.element(
				"listener-class").getTextTrim();

			ListenerDefinition listenerDefinition = null;

			try {
				Class<?> clazz = bundle.loadClass(listenerClass);

				Object listener = clazz.newInstance();

				listenerDefinition = new ListenerDefinition();

				listenerDefinition.setListener(listener);
				listenerDefinition.setContextParams(contextParams);
			}
			catch (Exception e) {
				_log.error("Omitted listener definition", e);

				continue;
			}

			listeners.add(listenerDefinition);
		}
	}

	protected static void loadFilters(
		Bundle bundle, Element root, WebXML webXML) {

		Map<String, FilterDefinition> filters = webXML.getFilters();

		for (Element filterMappingsEl : root.elements("filter-mapping")) {
			String filterName = filterMappingsEl.element(
				"filter-name").getTextTrim();
			String urlPattern = filterMappingsEl.element(
				"url-pattern").getTextTrim();

			Map<String, String> namespaces = new TreeMap<String, String>();

			namespaces.put("x", root.getNamespace().getText());

			XPath xPath = SAXReaderUtil.createXPath(
				"//x:filter[x:filter-name/text() ='" + filterName + "']",
				namespaces);

			Element filterElement = (Element)xPath.selectSingleNode(root);

			if (filterElement == null) {
				continue;
			}

			if (urlPattern.endsWith(_SLASH_STAR) && (urlPattern.length() > 2)) {
				urlPattern = urlPattern.substring(0, urlPattern.length() - 2);
			}

			if (urlPattern.startsWith(StringPool.STAR)) {
				urlPattern = StringPool.SLASH.concat(urlPattern);
			}

			String filterClass = filterElement.element(
				"filter-class").getTextTrim();

			FilterDefinition filterDefinition = null;

			try {
				Filter filterInstance = (Filter)bundle.loadClass(
					filterClass).newInstance();

				filterDefinition = new FilterDefinition();

				filterDefinition.setFilter(filterInstance);
				filterDefinition.setName(filterName);
			}
			catch (Exception e) {
				_log.error("Omitted filter definition", e);

				continue;
			}

			for (Element initParam : filterElement.elements("init-param")) {
				String initParamName = initParam.element(
					"param-name").getTextTrim();
				String initParamValue = initParam.element(
					"param-value").getTextTrim();

				filterDefinition.setInitParam(initParamName, initParamValue);
			}

			filters.put(urlPattern, filterDefinition);
		}
	}

	protected static void loadServlets(
		Bundle bundle, Element root, WebXML webXML) {

		Map<String, ServletDefinition> servlets = webXML.getServlets();

		for (Element servletMappingsEl : root.elements("servlet-mapping")) {
			String servletName = servletMappingsEl.element(
				"servlet-name").getTextTrim();
			String urlPattern = servletMappingsEl.element(
				"url-pattern").getTextTrim();

			Map<String, String> namespaces = new TreeMap<String, String>();

			namespaces.put("x", root.getNamespace().getText());

			XPath xPath = SAXReaderUtil.createXPath(
				"//x:servlet[x:servlet-name/text()='" + servletName + "']",
				namespaces);

			Element servletElement = (Element)xPath.selectSingleNode(root);

			if (servletElement == null) {
				continue;
			}

			if (urlPattern.endsWith(_SLASH_STAR) && (urlPattern.length() > 2)) {
				urlPattern = urlPattern.substring(0, urlPattern.length() - 2);
			}

			if (urlPattern.startsWith(StringPool.STAR)) {
				urlPattern = StringPool.SLASH.concat(urlPattern);
			}

			String servletClass = servletElement.element(
				"servlet-class").getTextTrim();

			ServletDefinition servletDefinition = null;

			try {
				Class<?> clazz = bundle.loadClass(servletClass);

				Servlet servletInstance = (Servlet)clazz.newInstance();

				servletDefinition = new ServletDefinition();

				servletDefinition.setServlet(servletInstance);
				servletDefinition.setName(servletName);
			}
			catch (Exception e) {
				_log.error("Omitted servlet definition", e);

				continue;
			}

			for (Element initParam : servletElement.elements("init-param")) {
				String initParamName = initParam.element(
					"param-name").getTextTrim();
				String initParamValue = initParam.element(
					"param-value").getTextTrim();

				servletDefinition.setInitParam(initParamName, initParamValue);
			}

			servlets.put(urlPattern, servletDefinition);
		}
	}

	private static final String _SLASH_STAR = "/*";

	private static Log _log = LogFactoryUtil.getLog(WebXMLLoader.class);

	private URL _defaultWeb;

}