/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.freemarker.FreeMarkerVariablesUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.ObjectWrapper;

import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Bruno Basto
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 */
public class DDMXSDImpl implements DDMXSD {

	public String getHTML(
			PageContext pageContext, Document document, Locale locale)
		throws Exception {

		return getHTML(pageContext, document.getRootElement(), locale);
	}

	public String getHTML(
			PageContext pageContext, Document document, Fields fields,
			Locale locale)
		throws Exception {

		return getHTML(pageContext, document.getRootElement(), fields, locale);
	}

	public String getHTML(
			PageContext pageContext, Document document, Fields fields,
			String namespace, boolean readOnly, Locale locale)
		throws Exception {

		return getHTML(
			pageContext, document.getRootElement(), fields, namespace,
			readOnly, locale);
	}

	public String getHTML(
			PageContext pageContext, Element element, Locale locale)
		throws Exception {

		return getHTML(pageContext, element, null, locale);
	}

	public String getHTML(
			PageContext pageContext, Element element, Fields fields,
			Locale locale)
		throws Exception {

		return getHTML(
			pageContext, element, fields, StringPool.BLANK, false, locale);
	}

	public String getHTML(
			PageContext pageContext, Element element, Fields fields,
			String namespace, boolean readOnly, Locale locale)
		throws Exception {

		StringBundler sb = new StringBundler();

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		String portletId = PortalUtil.getPortletId(request);

		String portletNamespace = PortalUtil.getPortletNamespace(portletId);

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			FreeMarkerContext freeMarkerContext = getFreeMarkerContext(
				dynamicElementElement, locale);

			freeMarkerContext.put("portletNamespace", portletNamespace);
			freeMarkerContext.put("namespace", namespace);

			if (fields != null) {
				freeMarkerContext.put("fields", fields);
			}

			Map<String, Object> field =
				(Map<String, Object>)freeMarkerContext.get("fieldStructure");

			String childrenHTML = getHTML(
				pageContext, dynamicElementElement, fields, namespace,
				readOnly, locale);

			field.put("children", childrenHTML);

			String fieldNamespace = dynamicElementElement.attributeValue(
				"fieldNamespace", _DEFAULT_NAMESPACE);

			if (readOnly) {
				fieldNamespace = _DEFAULT_READ_ONLY_NAMESPACE;
			}

			String type = dynamicElementElement.attributeValue("type");

			String templateName = StringUtil.replaceFirst(
				type, fieldNamespace.concat(StringPool.DASH), StringPool.BLANK);

			StringBundler resourcePath = new StringBundler(5);

			resourcePath.append(_TPL_PATH);
			resourcePath.append(fieldNamespace.toLowerCase());
			resourcePath.append(CharPool.SLASH);
			resourcePath.append(templateName);
			resourcePath.append(_TPL_EXT);

			sb.append(
				processFTL(
					pageContext, freeMarkerContext, resourcePath.toString(),
					readOnly));
		}

		return sb.toString();
	}

	public String getHTML(PageContext pageContext, String xml, Locale locale)
		throws Exception {

		return getHTML(pageContext, xml, null, locale);
	}

	public String getHTML(
			PageContext pageContext, String xml, Fields fields, Locale locale)
		throws Exception {

		return getHTML(pageContext, SAXReaderUtil.read(xml), fields, locale);
	}

	public String getHTML(
			PageContext pageContext, String xml, Fields fields,
			String namespace, Locale locale)
		throws Exception {

		return getHTML(
			pageContext, SAXReaderUtil.read(xml), fields, namespace, false,
			locale);
	}

	public String getHTML(
			PageContext pageContext, String xml, Fields fields,
			String namespace, boolean readOnly, Locale locale)
		throws Exception {

		return getHTML(
			pageContext, SAXReaderUtil.read(xml), fields, namespace, readOnly,
			locale);
	}

	public JSONArray getJSONArray(Document document) throws JSONException {
		return getJSONArray(document.getRootElement());
	}

	public JSONArray getJSONArray(Element element) throws JSONException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Document document = element.getDocument();

		String defaultLocale = LocalizationUtil.getDefaultLocale(
			document.asXML());

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			JSONObject localizationMapJSONObject =
				JSONFactoryUtil.createJSONObject();

			List<Element> metadataElements = dynamicElementElement.elements(
				"meta-data");

			for (Element metadataElement : metadataElements) {
				if (metadataElement == null) {
					continue;
				}

				String locale = metadataElement.attributeValue("locale");

				JSONObject localeMap = JSONFactoryUtil.createJSONObject();

				localizationMapJSONObject.put(locale, localeMap);

				for (Element metadataEntryElement :
						metadataElement.elements()) {

					String attributeName = metadataEntryElement.attributeValue(
						"name");
					String attributeValue = metadataEntryElement.getText();

					localeMap.put(attributeName, attributeValue);

					if (defaultLocale.equals(locale)) {
						jsonObject.put(attributeName, attributeValue);
					}
				}
			}

			jsonObject.put("localizationMap", localizationMapJSONObject);

			for (Attribute attribute : dynamicElementElement.attributes()) {
				jsonObject.put(attribute.getName(), attribute.getValue());
			}

			jsonObject.put(
				"id", dynamicElementElement.attributeValue("name"));

			String type = jsonObject.getString("type");

			String key = "fields";

			if (type.equals(_TYPE_RADIO) || type.equals(_TYPE_SELECT)) {
				key = "options";
			}

			jsonObject.put(key, getJSONArray(dynamicElementElement));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public JSONArray getJSONArray(String xml)
		throws DocumentException, JSONException {

		return getJSONArray(SAXReaderUtil.read(xml));
	}

	protected Map<String, Object> getFieldContext(
		Element dynamicElementElement, Locale locale) {

		Document document = dynamicElementElement.getDocument();

		String[] availableLocales = LocalizationUtil.getAvailableLocales(
			document.asXML());

		String defaultLanguageId = LocalizationUtil.getDefaultLocale(
			document.asXML());

		String languageId = LocaleUtil.toLanguageId(locale);

		if (!ArrayUtil.contains(availableLocales, languageId)) {
			languageId = defaultLanguageId;
		}

		Element metadataElement =
			(Element)dynamicElementElement.selectSingleNode(
				"meta-data[@locale='" + languageId  + "']");

		Map<String, Object> field = new HashMap<String, Object>();

		if (metadataElement != null) {
			for (Element metadataEntry : metadataElement.elements()) {
				field.put(
					metadataEntry.attributeValue("name"),
					metadataEntry.getText());
			}
		}

		for (Attribute attribute : dynamicElementElement.attributes()) {
			field.put(attribute.getName(), attribute.getValue());
		}

		return field;
	}

	protected FreeMarkerContext getFreeMarkerContext(
		Element dynamicElementElement, Locale locale) {

		FreeMarkerContext freeMarkerContext =
			FreeMarkerEngineUtil.getWrappedStandardToolsContext();

		Map<String, Object> fieldContext = getFieldContext(
			dynamicElementElement, locale);

		Map<String, Object> parentFieldContext = new HashMap<String, Object>();

		Element parentElement = dynamicElementElement.getParent();

		if (parentElement != null) {
			parentFieldContext = getFieldContext(parentElement, locale);
		}

		freeMarkerContext.put("fieldStructure", fieldContext);
		freeMarkerContext.put("parentFieldStructure", parentFieldContext);

		return freeMarkerContext;
	}

	/**
	 * @see com.liferay.taglib.util.ThemeUtil#includeFTL
	 */
	protected String processFTL(
			PageContext pageContext, FreeMarkerContext freeMarkerContext,
			String resourcePath, boolean readOnly)
		throws Exception {

		if (!FreeMarkerEngineUtil.resourceExists(resourcePath)) {
			if (readOnly) {
				resourcePath = _TPL_DEFAULT_READ_ONLY_PATH;
			}
			else {
				resourcePath = _TPL_DEFAULT_PATH;
			}
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		// FreeMarker variables

		FreeMarkerVariablesUtil.insertVariables(
			freeMarkerContext, request);

		// Tag libraries

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		Writer writer = new UnsyncStringWriter();

		// Portal JSP tag library factory

		TaglibFactory portalTaglib = new TaglibFactory(
			pageContext.getServletContext());

		freeMarkerContext.put("PortalJspTagLibs", portalTaglib);

		// FreeMarker JSP tag library support

		final Servlet servlet = (Servlet)pageContext.getPage();

		GenericServlet genericServlet = null;

		if (servlet instanceof GenericServlet) {
			genericServlet = (GenericServlet)servlet;
		}
		else {
			genericServlet = new GenericServlet() {

				@Override
				public void service(
						ServletRequest servletRequest,
						ServletResponse servletResponse)
					throws ServletException, IOException {

					servlet.service(servletRequest, servletResponse);
				}

			};

			genericServlet.init(pageContext.getServletConfig());
		}

		ServletContextHashModel servletContextHashModel =
			new ServletContextHashModel(
				genericServlet, ObjectWrapper.DEFAULT_WRAPPER);

		freeMarkerContext.put("Application", servletContextHashModel);

		HttpRequestHashModel httpRequestHashModel = new HttpRequestHashModel(
			request, response, ObjectWrapper.DEFAULT_WRAPPER);

		freeMarkerContext.put("Request", httpRequestHashModel);

		// Merge templates

		FreeMarkerEngineUtil.mergeTemplate(
			resourcePath, freeMarkerContext, writer);

		return writer.toString();
	}

	private static final String _DEFAULT_NAMESPACE = "alloy";

	private static final String _DEFAULT_READ_ONLY_NAMESPACE = "readonly";

	private static final String _TPL_DEFAULT_PATH =
		"com/liferay/portlet/dynamicdatamapping/dependencies/alloy/text.ftl";

	private static final String _TPL_DEFAULT_READ_ONLY_PATH =
		"com/liferay/portlet/dynamicdatamapping/dependencies/readonly/" +
			"default.ftl";

	private static final String _TPL_EXT = ".ftl";

	private static final String _TPL_PATH =
		"com/liferay/portlet/dynamicdatamapping/dependencies/";

	private static final String _TYPE_RADIO = "radio";

	private static final String _TYPE_SELECT = "select";

}