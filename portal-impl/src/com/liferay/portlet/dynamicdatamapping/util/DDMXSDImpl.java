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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.util.PwdGenerator;
import com.liferay.util.freemarker.FreeMarkerTaglibFactoryUtil;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;

import java.io.IOException;
import java.io.Writer;

import java.net.URL;

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
 * @author Marcellus Tavares
 */
@DoPrivileged
public class DDMXSDImpl implements DDMXSD {

	public DDMXSDImpl() {
		String defaultTemplateId = _TPL_PATH + "alloy/text.ftl";

		URL defaultTemplateURL = getResource(defaultTemplateId);

		_defaultTemplateResource = new URLTemplateResource(
			defaultTemplateId, defaultTemplateURL);

		String defaultReadOnlyTemplateId = _TPL_PATH + "readonly/default.ftl";

		URL defaultReadOnlyTemplateURL = getResource(defaultReadOnlyTemplateId);

		_defaultReadOnlyTemplateResource = new URLTemplateResource(
			defaultReadOnlyTemplateId, defaultReadOnlyTemplateURL);
	}

	public String getFieldHTML(
			PageContext pageContext, Element element, Fields fields,
			String portletNamespace, String namespace, String mode,
			boolean readOnly, Locale locale)
		throws Exception {

		Map<String, Object> freeMarkerContext = getFreeMarkerContext(
			pageContext, portletNamespace, namespace, element, locale);

		Map<String, Object> fieldStructure =
			(Map<String, Object>)freeMarkerContext.get("fieldStructure");

		int fieldRepetition = 1;

		DDMFieldsCounter ddmFieldsCounter = getFieldsCounter(
			pageContext, fields, portletNamespace, namespace);

		String name = element.attributeValue("name");

		if (fields != null) {
			freeMarkerContext.put("fields", fields);

			Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

			if ((fieldsDisplayField != null) && fields.contains(name)) {
				String[] fieldsDisplayValues = DDMUtil.getFieldsDisplayValues(
					fieldsDisplayField);

				Map<String, Object> parentFieldStructure =
					(Map<String, Object>)freeMarkerContext.get(
						"parentFieldStructure");

				String parentFieldName = (String)parentFieldStructure.get(
					"name");

				int offset = ddmFieldsCounter.get(DDMImpl.FIELDS_DISPLAY_NAME);

				fieldRepetition = countFieldRepetition(
					fieldsDisplayValues, parentFieldName, offset);
			}
		}

		StringBundler sb = new StringBundler(fieldRepetition);

		while (fieldRepetition > 0) {
			fieldStructure.put("fieldNamespace", PwdGenerator.getPassword(4));
			fieldStructure.put("valueIndex", ddmFieldsCounter.get(name));

			if ((fields != null) && fields.contains(name)) {
				ddmFieldsCounter.incrementKey(name);
				ddmFieldsCounter.incrementKey(DDMImpl.FIELDS_DISPLAY_NAME);
			}

			String childrenHTML = getHTML(
				pageContext, element, fields, portletNamespace, namespace, mode,
				readOnly, locale);

			fieldStructure.put("children", childrenHTML);

			sb.append(
				processFTL(
					pageContext, element, mode, readOnly, freeMarkerContext));

			fieldRepetition--;
		}

		return sb.toString();
	}

	public String getFieldHTML(
			PageContext pageContext, Element element, String portletNamespace,
			String namespace, String mode, boolean readOnly, Locale locale)
		throws Exception {

		Map<String, Object> freeMarkerContext = getFreeMarkerContext(
			pageContext, portletNamespace, namespace, element, locale);

		freeMarkerContext.put("ignoreRepeatable", Boolean.TRUE);

		Map<String, Object> fieldStructure =
			(Map<String, Object>)freeMarkerContext.get("fieldStructure");

		DDMFieldsCounter ddmFieldsCounter = getFieldsCounter(
			pageContext, null, portletNamespace, namespace);

		String name = element.attributeValue("name");

		fieldStructure.put("fieldNamespace", PwdGenerator.getPassword(4));
		fieldStructure.put("valueIndex", ddmFieldsCounter.get(name));

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		StringBundler sb = new StringBundler(dynamicElementElements.size());

		for (Element dynamicElementElement : dynamicElementElements) {
			String type = dynamicElementElement.attributeValue("type");

			if (!type.equals("option")) {
				sb.append(StringPool.BLANK);
			}
			else {
				sb.append(
					getFieldHTML(
						pageContext, dynamicElementElement, portletNamespace,
						namespace, mode, readOnly, locale));
			}
		}

		fieldStructure.put("children", sb.toString());

		return processFTL(
			pageContext, element, mode, readOnly, freeMarkerContext);
	}

	public String getFieldHTMLByName(
			PageContext pageContext, long classNameId, long classPK,
			String fieldName, Fields fields, String portletNamespace,
			String namespace, String mode, boolean readOnly, Locale locale)
		throws Exception {

		String xsd = getXSD(classNameId, classPK);

		Document document = SAXReaderUtil.read(xsd);

		String xPathExpression =
			"//dynamic-element[@name=".concat(
				HtmlUtil.escapeXPathAttribute(fieldName)).concat("]");

		XPath xPathSelector = SAXReaderUtil.createXPath(xPathExpression);

		Node node = xPathSelector.selectSingleNode(document.getRootElement());

		Element element = (Element)node.asXPathResult(node.getParent());

		return getFieldHTML(
			pageContext, element, fields, portletNamespace, namespace, mode,
			readOnly, locale);
	}

	public String getFieldHTMLByName(
			PageContext pageContext, long classNameId, long classPK,
			String fieldName, String portletNamespace, String namespace,
			String mode, boolean readOnly, Locale locale)
		throws Exception {

		String xsd = getXSD(classNameId, classPK);

		Document document = SAXReaderUtil.read(xsd);

		String xPathExpression =
			"//dynamic-element[@name=".concat(
				HtmlUtil.escapeXPathAttribute(fieldName)).concat("]");

		XPath xPathSelector = SAXReaderUtil.createXPath(xPathExpression);

		Node node = xPathSelector.selectSingleNode(document.getRootElement());

		Element element = (Element)node.asXPathResult(node.getParent());

		return getFieldHTML(
			pageContext, element, portletNamespace, namespace, mode, readOnly,
			locale);
	}

	public String getHTML(
			PageContext pageContext, DDMStructure ddmStructure, Fields fields,
			String portletNamespace, String namespace, boolean readOnly,
			Locale locale)
		throws Exception {

		return getHTML(
			pageContext, ddmStructure.getXsd(), fields, portletNamespace,
			namespace, readOnly, locale);
	}

	public String getHTML(
			PageContext pageContext, DDMTemplate ddmTemplate, Fields fields,
			String portletNamespace, String namespace, boolean readOnly,
			Locale locale)
		throws Exception {

		return getHTML(
			pageContext, ddmTemplate.getScript(), fields, portletNamespace,
			namespace, ddmTemplate.getMode(), readOnly, locale);
	}

	public String getHTML(
			PageContext pageContext, Element element, Fields fields,
			String portletNamespace, Locale locale)
		throws Exception {

		return getHTML(
			pageContext, element, fields, portletNamespace, StringPool.BLANK,
			null, false, locale);
	}

	public String getHTML(
			PageContext pageContext, Element element, Fields fields,
			String portletNamespace, String namespace, String mode,
			boolean readOnly, Locale locale)
		throws Exception {

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		StringBundler sb = new StringBundler(dynamicElementElements.size());

		for (Element dynamicElementElement : dynamicElementElements) {
			sb.append(
				getFieldHTML(
					pageContext, dynamicElementElement, fields,
					portletNamespace, namespace, mode, readOnly, locale));
		}

		return sb.toString();
	}

	public String getHTML(
			PageContext pageContext, Element element, String portletNamespace,
			Locale locale)
		throws Exception {

		return getHTML(pageContext, element, null, portletNamespace, locale);
	}

	public String getHTML(
			PageContext pageContext, String xml, Fields fields,
			String portletNamespace, Locale locale)
		throws Exception {

		return getHTML(
			pageContext, xml, fields, portletNamespace, StringPool.BLANK,
			locale);
	}

	public String getHTML(
			PageContext pageContext, String xml, Fields fields,
			String portletNamespace, String namespace, boolean readOnly,
			Locale locale)
		throws Exception {

		return getHTML(
			pageContext, xml, fields, portletNamespace, namespace, null,
			readOnly, locale);
	}

	public String getHTML(
			PageContext pageContext, String xml, Fields fields,
			String portletNamespace, String namespace, Locale locale)
		throws Exception {

		return getHTML(
			pageContext, xml, fields, portletNamespace, namespace, false,
			locale);
	}

	public String getHTML(
			PageContext pageContext, String xml, Fields fields,
			String portletNamespace, String namespace, String mode,
			boolean readOnly, Locale locale)
		throws Exception {

		Document document = SAXReaderUtil.read(xml);

		return getHTML(
			pageContext, document.getRootElement(), fields, portletNamespace,
			namespace, mode, readOnly, locale);
	}

	public String getHTML(
			PageContext pageContext, String xml, String portletNamespace,
			Locale locale)
		throws Exception {

		return getHTML(pageContext, xml, null, locale);
	}

	public JSONArray getJSONArray(DDMStructure structure, String xsd)
		throws PortalException, SystemException {

		JSONArray jsonArray = null;

		if (Validator.isNull(xsd)) {
			jsonArray = getJSONArray(structure.getDocument());
		}
		else {
			jsonArray = getJSONArray(xsd);
		}

		addStructureFieldAttributes(structure, jsonArray);

		return jsonArray;
	}

	public JSONArray getJSONArray(Document document) throws PortalException {
		return getJSONArray(document.getRootElement());
	}

	public JSONArray getJSONArray(Element element) throws PortalException {
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

			for (Attribute attribute : dynamicElementElement.attributes()) {
				jsonObject.put(attribute.getName(), attribute.getValue());
			}

			jsonObject.put("autoGeneratedName", false);
			jsonObject.put("id", dynamicElementElement.attributeValue("name"));

			String type = jsonObject.getString("type");

			List<Element> metadataElements = dynamicElementElement.elements(
				"meta-data");

			for (Element metadataElement : metadataElements) {
				if (metadataElement == null) {
					continue;
				}

				String locale = metadataElement.attributeValue("locale");

				JSONObject localeMap = JSONFactoryUtil.createJSONObject();

				for (Element metadataEntryElement :
						metadataElement.elements()) {

					String attributeName = metadataEntryElement.attributeValue(
						"name");
					String attributeValue = metadataEntryElement.getTextTrim();

					putMetadataValue(
						localeMap, attributeName, attributeValue, type);

					if (defaultLocale.equals(locale)) {
						putMetadataValue(
							jsonObject, attributeName, attributeValue, type);
					}
				}

				localizationMapJSONObject.put(locale, localeMap);
			}

			jsonObject.put("localizationMap", localizationMapJSONObject);

			JSONArray hiddenAttributesJSONArray =
				JSONFactoryUtil.createJSONArray();

			if (type.equals(DDMImpl.TYPE_CHECKBOX)) {
				hiddenAttributesJSONArray.put("required");
			}

			if (type.equals(DDMImpl.TYPE_DDM_FILEUPLOAD)) {
				hiddenAttributesJSONArray.put("predefinedValue");
			}

			hiddenAttributesJSONArray.put("readOnly");

			jsonObject.put("hiddenAttributes", hiddenAttributesJSONArray);

			String key = "fields";

			if (type.equals(DDMImpl.TYPE_RADIO) ||
				type.equals(DDMImpl.TYPE_SELECT)) {

				key = "options";
			}

			jsonObject.put(key, getJSONArray(dynamicElementElement));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public JSONArray getJSONArray(String xml)
		throws PortalException, SystemException {

		try {
			return getJSONArray(SAXReaderUtil.read(xml));
		}
		catch (DocumentException de) {
			throw new SystemException();
		}
	}

	public String getXSD(long classNameId, long classPK)
		throws PortalException, SystemException {

		if ((classNameId <= 0) || (classPK <= 0)) {
			return null;
		}

		long ddmStructureClassNameId = PortalUtil.getClassNameId(
			DDMStructure.class);

		long ddmTemplateClassNameId = PortalUtil.getClassNameId(
			DDMTemplate.class);

		if (classNameId == ddmStructureClassNameId) {
			DDMStructure structure = DDMStructureServiceUtil.getStructure(
				classPK);

			return structure.getCompleteXsd();
		}
		else if (classNameId == ddmTemplateClassNameId) {
			DDMTemplate template = DDMTemplateServiceUtil.getTemplate(classPK);

			return template.getScript();
		}

		return null;
	}

	protected JSONArray addStructureFieldAttributes(
		DDMStructure structure, JSONArray jsonArray) {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String fieldName = jsonObject.getString("name");

			jsonObject.put(
				"readOnlyAttributes",
				getStructureFieldReadOnlyAttributes(structure, fieldName));
		}

		return jsonArray;
	}

	protected int countFieldRepetition(
		String[] fieldsDisplayValues, String parentFieldName, int offset) {

		int total = 0;

		String fieldName = fieldsDisplayValues[offset];

		for (; offset < fieldsDisplayValues.length; offset++) {
			String fieldNameValue = fieldsDisplayValues[offset];

			if (fieldNameValue.equals(fieldName)) {
				total++;
			}

			if (fieldNameValue.equals(parentFieldName)) {
				break;
			}
		}

		return total;
	}

	protected Map<String, Object> getFieldContext(
		PageContext pageContext, String portletNamespace, String namespace,
		Element dynamicElementElement, Locale locale) {

		Map<String, Map<String, Object>> fieldsContext = getFieldsContext(
			pageContext, portletNamespace, namespace);

		String name = dynamicElementElement.attributeValue("name");

		Map<String, Object> fieldContext = fieldsContext.get(name);

		if (fieldContext != null) {
			return fieldContext;
		}

		Document document = dynamicElementElement.getDocument();

		String xml = document.asXML();

		String[] availableLocales = LocalizationUtil.getAvailableLocales(xml);

		String defaultLanguageId = LocalizationUtil.getDefaultLocale(xml);

		String languageId = LocaleUtil.toLanguageId(locale);

		if (!ArrayUtil.contains(availableLocales, languageId)) {
			languageId = defaultLanguageId;
		}

		Element metadataElement =
			(Element)dynamicElementElement.selectSingleNode(
				"meta-data[@locale='" + languageId + "']");

		fieldContext = new HashMap<String, Object>();

		if (metadataElement != null) {
			for (Element metadataEntry : metadataElement.elements()) {
				fieldContext.put(
					metadataEntry.attributeValue("name"),
					metadataEntry.getText());
			}
		}

		for (Attribute attribute : dynamicElementElement.attributes()) {
			fieldContext.put(attribute.getName(), attribute.getValue());
		}

		fieldContext.put("fieldNamespace", PwdGenerator.getPassword(4));

		fieldsContext.put(name, fieldContext);

		return fieldContext;
	}

	protected Map<String, Map<String, Object>> getFieldsContext(
		PageContext pageContext, String portletNamespace, String namespace) {

		String fieldsContextKey =
			portletNamespace + namespace + "fieldsContext";

		Map<String, Map<String, Object>> fieldsContext =
			(Map<String, Map<String, Object>>)pageContext.getAttribute(
				fieldsContextKey);

		if (fieldsContext == null) {
			fieldsContext = new HashMap<String, Map<String, Object>>();

			pageContext.setAttribute(fieldsContextKey, fieldsContext);
		}

		return fieldsContext;
	}

	protected DDMFieldsCounter getFieldsCounter(
		PageContext pageContext, Fields fields, String portletNamespace,
		String namespace) {

		String fieldsCounterKey = portletNamespace + namespace + "fieldsCount";

		DDMFieldsCounter ddmFieldsCounter =
			(DDMFieldsCounter)pageContext.getAttribute(fieldsCounterKey);

		if (ddmFieldsCounter == null) {
			ddmFieldsCounter = new DDMFieldsCounter();

			pageContext.setAttribute(fieldsCounterKey, ddmFieldsCounter);
		}

		return ddmFieldsCounter;
	}

	protected Map<String, Object> getFreeMarkerContext(
		PageContext pageContext, String portletNamespace, String namespace,
		Element dynamicElementElement, Locale locale) {

		Map<String, Object> freeMarkerContext = new HashMap<String, Object>();

		Map<String, Object> fieldContext = getFieldContext(
			pageContext, portletNamespace, namespace, dynamicElementElement,
			locale);

		Map<String, Object> parentFieldContext = new HashMap<String, Object>();

		Element parentElement = dynamicElementElement.getParent();

		if (parentElement != null) {
			parentFieldContext = getFieldContext(
				pageContext, portletNamespace, namespace, parentElement,
				locale);
		}

		freeMarkerContext.put("fieldStructure", fieldContext);
		freeMarkerContext.put("namespace", namespace);
		freeMarkerContext.put("parentFieldStructure", parentFieldContext);
		freeMarkerContext.put("portletNamespace", portletNamespace);
		freeMarkerContext.put("requestedLocale", locale);

		return freeMarkerContext;
	}

	protected URL getResource(String name) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResource(name);
	}

	protected JSONArray getStructureFieldReadOnlyAttributes(
		DDMStructure structure, String fieldName) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		try {
			if (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(
					structure.getStructureId()) > 0) {

				jsonArray.put("name");
			}
		}
		catch (Exception e) {
		}

		return jsonArray;
	}

	protected String processFTL(
			PageContext pageContext, Element element, String mode,
			boolean readOnly, Map<String, Object> freeMarkerContext)
		throws Exception {

		String fieldNamespace = element.attributeValue(
			"fieldNamespace", _DEFAULT_NAMESPACE);

		TemplateResource templateResource = _defaultTemplateResource;

		Map<String, Object> fieldStructure =
			(Map<String, Object>)freeMarkerContext.get("fieldStructure");

		boolean fieldReadOnly = GetterUtil.getBoolean(
			fieldStructure.get("readOnly"));

		if ((fieldReadOnly && Validator.isNotNull(mode) &&
			 mode.equalsIgnoreCase(
				DDMTemplateConstants.TEMPLATE_MODE_EDIT)) || readOnly) {

			fieldNamespace = _DEFAULT_READ_ONLY_NAMESPACE;

			templateResource = _defaultReadOnlyTemplateResource;
		}

		String type = element.attributeValue("type");

		String templateName = StringUtil.replaceFirst(
			type, fieldNamespace.concat(StringPool.DASH), StringPool.BLANK);

		StringBundler resourcePath = new StringBundler(5);

		resourcePath.append(_TPL_PATH);
		resourcePath.append(fieldNamespace.toLowerCase());
		resourcePath.append(CharPool.SLASH);
		resourcePath.append(templateName);
		resourcePath.append(_TPL_EXT);

		String resource = resourcePath.toString();

		URL url = getResource(resource);

		if (url != null) {
			templateResource = new URLTemplateResource(resource, url);
		}

		if (templateResource == null) {
			throw new Exception("Unable to load template resource " + resource);
		}

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, templateResource,
			TemplateContextType.STANDARD);

		for (Map.Entry<String, Object> entry : freeMarkerContext.entrySet()) {
			template.put(entry.getKey(), entry.getValue());
		}

		return processFTL(pageContext, template);
	}

	/**
	 * @see com.liferay.taglib.util.ThemeUtil#includeFTL
	 */
	protected String processFTL(PageContext pageContext, Template template)
		throws Exception {

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		// FreeMarker variables

		template.prepare(request);

		// Tag libraries

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		Writer writer = new UnsyncStringWriter();

		// Portal JSP tag library factory

		TemplateHashModel portalTaglib =
			FreeMarkerTaglibFactoryUtil.createTaglibFactory(
				pageContext.getServletContext());

		template.put("PortalJspTagLibs", portalTaglib);

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
					throws IOException, ServletException {

					servlet.service(servletRequest, servletResponse);
				}

			};

			genericServlet.init(pageContext.getServletConfig());
		}

		ServletContextHashModel servletContextHashModel =
			new ServletContextHashModel(
				genericServlet, ObjectWrapper.DEFAULT_WRAPPER);

		template.put("Application", servletContextHashModel);

		HttpRequestHashModel httpRequestHashModel = new HttpRequestHashModel(
			request, response, ObjectWrapper.DEFAULT_WRAPPER);

		template.put("Request", httpRequestHashModel);

		// Merge templates

		template.processTemplate(writer);

		return writer.toString();
	}

	protected void putMetadataValue(
		JSONObject jsonObject, String attributeName, String attributeValue,
		String type) {

		if (type.equals(DDMImpl.TYPE_RADIO) ||
			type.equals(DDMImpl.TYPE_SELECT)) {

			if (attributeName.equals("predefinedValue")) {
				try {
					jsonObject.put(
						attributeName,
						JSONFactoryUtil.createJSONArray(attributeValue));
				}
				catch (Exception e) {
				}

				return;
			}
		}

		jsonObject.put(attributeName, attributeValue);
	}

	private static final String _DEFAULT_NAMESPACE = "alloy";

	private static final String _DEFAULT_READ_ONLY_NAMESPACE = "readonly";

	private static final String _TPL_EXT = ".ftl";

	private static final String _TPL_PATH =
		"com/liferay/portlet/dynamicdatamapping/dependencies/";

	private TemplateResource _defaultReadOnlyTemplateResource;
	private TemplateResource _defaultTemplateResource;

}