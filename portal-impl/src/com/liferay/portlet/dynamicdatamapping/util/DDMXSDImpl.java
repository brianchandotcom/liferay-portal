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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
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
import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.Writer;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@Override
	public String getFieldHTML(
			HttpServletRequest request, HttpServletResponse response,
			Element element, Fields fields, String portletNamespace,
			String namespace, String mode, boolean readOnly, Locale locale)
		throws Exception {

		Map<String, Object> freeMarkerContext = getFreeMarkerContext(
			request, response, portletNamespace, namespace, element, locale);

		if (fields != null) {
			freeMarkerContext.put("fields", fields);
		}

		Map<String, Object> fieldStructure =
			(Map<String, Object>)freeMarkerContext.get("fieldStructure");

		int fieldRepetition = 1;
		int offset = 0;

		DDMFieldsCounter ddmFieldsCounter = getFieldsCounter(
			request, response, fields, portletNamespace, namespace);

		String name = element.attributeValue("name");

		String fieldDisplayValue = getFieldsDisplayValue(
			request, response, fields);

		String[] fieldsDisplayValues = getFieldsDisplayValues(
			fieldDisplayValue);

		boolean fieldDisplayable = ArrayUtil.contains(
			fieldsDisplayValues, name);

		if (fieldDisplayable) {
			Map<String, Object> parentFieldStructure =
				(Map<String, Object>)freeMarkerContext.get(
					"parentFieldStructure");

			String parentFieldName = (String)parentFieldStructure.get("name");

			offset = getFieldOffset(
				fieldsDisplayValues, name, ddmFieldsCounter.get(name));

			if (offset == fieldsDisplayValues.length) {
				return StringPool.BLANK;
			}

			fieldRepetition = countFieldRepetition(
				fieldsDisplayValues, parentFieldName, offset);
		}

		StringBundler sb = new StringBundler(fieldRepetition);

		while (fieldRepetition > 0) {
			offset = getFieldOffset(
				fieldsDisplayValues, name, ddmFieldsCounter.get(name));

			String fieldNamespace = StringUtil.randomId();

			if (fieldDisplayable) {
				fieldNamespace = getFieldNamespace(
					fieldDisplayValue, ddmFieldsCounter, offset);
			}

			fieldStructure.put("fieldNamespace", fieldNamespace);

			fieldStructure.put("valueIndex", ddmFieldsCounter.get(name));

			if (fieldDisplayable) {
				ddmFieldsCounter.incrementKey(name);
			}

			String childrenHTML = getHTML(
				request, response, element, fields, portletNamespace, namespace,
				mode, readOnly, locale);

			fieldStructure.put("children", childrenHTML);

			boolean disabled = GetterUtil.getBoolean(
				fieldStructure.get("disabled"), false);

			if (disabled) {
				readOnly = true;
			}

			sb.append(
				processFTL(
					request, response, element, mode, readOnly,
					freeMarkerContext));

			fieldRepetition--;
		}

		return sb.toString();
	}

	@Override
	public String getFieldHTMLByName(
			HttpServletRequest request, HttpServletResponse response,
			long classNameId, long classPK, String fieldName, Fields fields,
			String portletNamespace, String namespace, String mode,
			boolean readOnly, Locale locale)
		throws Exception {

		String xsd = getXSD(classNameId, classPK);

		Document document = SAXReaderUtil.read(xsd);

		String xPathExpression = "//dynamic-element[@name=".concat(
			HtmlUtil.escapeXPathAttribute(fieldName)).concat("]");

		XPath xPathSelector = SAXReaderUtil.createXPath(xPathExpression);

		Node node = xPathSelector.selectSingleNode(document.getRootElement());

		Element element = (Element)node.asXPathResult(node.getParent());

		return getFieldHTML(
			request, response, element, fields, portletNamespace, namespace,
			mode, readOnly, locale);
	}

	@Override
	public String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			DDMStructure ddmStructure, Fields fields, String portletNamespace,
			String namespace, boolean readOnly, Locale locale)
		throws Exception {

		return getHTML(
			request, response, ddmStructure.getDefinition(), fields,
			portletNamespace, namespace, readOnly, locale);
	}

	@Override
	public String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			DDMTemplate ddmTemplate, Fields fields, String portletNamespace,
			String namespace, boolean readOnly, Locale locale)
		throws Exception {

		return getHTML(
			request, response, ddmTemplate.getScript(), fields,
			portletNamespace, namespace, ddmTemplate.getMode(), readOnly,
			locale);
	}

	public String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			Element element, Fields fields, String portletNamespace,
			Locale locale)
		throws Exception {

		return getHTML(
			request, response, element, fields, portletNamespace,
			StringPool.BLANK, null, false, locale);
	}

	public String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			Element element, Fields fields, String portletNamespace,
			String namespace, String mode, boolean readOnly, Locale locale)
		throws Exception {

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		StringBundler sb = new StringBundler(dynamicElementElements.size());

		for (Element dynamicElementElement : dynamicElementElements) {
			sb.append(
				getFieldHTML(
					request, response, dynamicElementElement, fields,
					portletNamespace, namespace, mode, readOnly, locale));
		}

		return sb.toString();
	}

	public String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			Element element, String portletNamespace, Locale locale)
		throws Exception {

		return getHTML(
			request, response, element, null, portletNamespace, locale);
	}

	@Override
	public String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			String xml, Fields fields, String portletNamespace, Locale locale)
		throws Exception {

		return getHTML(
			request, response, xml, fields, portletNamespace, StringPool.BLANK,
			locale);
	}

	@Override
	public String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			String xml, Fields fields, String portletNamespace,
			String namespace, boolean readOnly, Locale locale)
		throws Exception {

		return getHTML(
			request, response, xml, fields, portletNamespace, namespace, null,
			readOnly, locale);
	}

	@Override
	public String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			String xml, Fields fields, String portletNamespace,
			String namespace, Locale locale)
		throws Exception {

		return getHTML(
			request, response, xml, fields, portletNamespace, namespace, false,
			locale);
	}

	@Override
	public String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			String xml, Fields fields, String portletNamespace,
			String namespace, String mode, boolean readOnly, Locale locale)
		throws Exception {

		Document document = SAXReaderUtil.read(xml);

		return getHTML(
			request, response, document.getRootElement(), fields,
			portletNamespace, namespace, mode, readOnly, locale);
	}

	@Override
	public String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			String xml, String portletNamespace, Locale locale)
		throws Exception {

		return getHTML(request, response, xml, null, locale);
	}

	@Override
	public JSONArray getJSONArray(DDMStructure structure, String xsd)
		throws PortalException {

		JSONArray jsonArray = null;

		if (Validator.isNull(xsd)) {
			jsonArray = getJSONArray(structure.getDefinition());
		}
		else {
			jsonArray = getJSONArray(xsd);
		}

		addStructureFieldAttributes(structure, jsonArray);

		return jsonArray;
	}

	@Override
	public JSONArray getJSONArray(Document document) throws PortalException {
		return getJSONArray(document.getRootElement());
	}

	@Override
	public JSONArray getJSONArray(Element element) throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Document document = element.getDocument();

		String defaultLanguageId = LocalizationUtil.getDefaultLanguageId(
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

					if (defaultLanguageId.equals(locale)) {
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

	@Override
	public JSONArray getJSONArray(String xml) throws PortalException {
		try {
			return getJSONArray(SAXReaderUtil.read(xml));
		}
		catch (DocumentException de) {
			throw new SystemException();
		}
	}

	@Override
	public String getSimpleFieldHTML(
			HttpServletRequest request, HttpServletResponse response,
			Element element, Field field, String portletNamespace,
			String namespace, String mode, boolean readOnly, Locale locale)
		throws Exception {

		Map<String, Object> freeMarkerContext = getFreeMarkerContext(
			request, response, portletNamespace, namespace, element, locale);

		freeMarkerContext.put("ignoreRepeatable", Boolean.TRUE);

		Map<String, Object> fieldStructure =
			(Map<String, Object>)freeMarkerContext.get("fieldStructure");

		DDMFieldsCounter ddmFieldsCounter = getFieldsCounter(
			request, response, null, portletNamespace, namespace);

		String name = element.attributeValue("name");

		if ((field != null) && Validator.isNotNull(field.getValue())) {
			Fields fields = new Fields();

			fields.put(field);

			freeMarkerContext.put("fields", fields);
		}

		fieldStructure.put("fieldNamespace", StringUtil.randomId());
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
					getSimpleFieldHTML(
						request, response, dynamicElementElement, field,
						portletNamespace, namespace, mode, readOnly, locale));
			}
		}

		fieldStructure.put("children", sb.toString());

		return processFTL(
			request, response, element, mode, readOnly, freeMarkerContext);
	}

	@Override
	public String getSimpleFieldHTMLByName(
			HttpServletRequest request, HttpServletResponse response,
			long classNameId, long classPK, Field field,
			String portletNamespace, String namespace, String mode,
			boolean readOnly, Locale locale)
		throws Exception {

		String xsd = getXSD(classNameId, classPK);

		Document document = SAXReaderUtil.read(xsd);

		String xPathExpression = "//dynamic-element[@name=".concat(
			HtmlUtil.escapeXPathAttribute(field.getName())).concat("]");

		XPath xPathSelector = SAXReaderUtil.createXPath(xPathExpression);

		Node node = xPathSelector.selectSingleNode(document.getRootElement());

		Element element = (Element)node.asXPathResult(node.getParent());

		return getSimpleFieldHTML(
			request, response, element, field, portletNamespace, namespace,
			mode, readOnly, locale);
	}

	@Override
	public String getXSD(long classNameId, long classPK)
		throws PortalException {

		if ((classNameId <= 0) || (classPK <= 0)) {
			return null;
		}

		long ddmStructureClassNameId = PortalUtil.getClassNameId(
			DDMStructure.class);

		long ddmTemplateClassNameId = PortalUtil.getClassNameId(
			DDMTemplate.class);

		if (classNameId == ddmStructureClassNameId) {
			DDMStructure structure = DDMStructureLocalServiceUtil.getStructure(
				classPK);

			DDMForm ddmForm = structure.getFullHierarchyDDMForm();

			return DDMFormXSDSerializerUtil.serialize(ddmForm);
		}
		else if (classNameId == ddmTemplateClassNameId) {
			DDMTemplate template = DDMTemplateLocalServiceUtil.getTemplate(
				classPK);

			return template.getScript();
		}

		return null;
	}

	@Override
	public String getXSD(String json) throws PortalException {
		DDMForm ddmForm = DDMFormJSONDeserializerUtil.deserialize(json);

		return DDMFormXSDSerializerUtil.serialize(ddmForm);
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
		HttpServletRequest request, HttpServletResponse response,
		String portletNamespace, String namespace,
		Element dynamicElementElement, Locale locale) {

		Map<String, Map<String, Object>> fieldsContext = getFieldsContext(
			request, response, portletNamespace, namespace);

		String name = dynamicElementElement.attributeValue("name");

		Map<String, Object> fieldContext = fieldsContext.get(name);

		if (fieldContext != null) {
			return fieldContext;
		}

		Document document = dynamicElementElement.getDocument();

		String[] availableLanguageIds =
			LocalizationUtil.getAvailableLanguageIds(document);

		String defaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			document);

		String editingLanguageId = LocaleUtil.toLanguageId(locale);

		String structureLanguageId = editingLanguageId;

		if (!ArrayUtil.contains(availableLanguageIds, editingLanguageId)) {
			structureLanguageId = defaultLanguageId;
		}

		Element metadataElement =
			(Element)dynamicElementElement.selectSingleNode(
				"meta-data[@locale='" + structureLanguageId + "']");

		fieldContext = new HashMap<>();

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

		boolean localizable = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("localizable"), true);

		if (!localizable && !editingLanguageId.equals(defaultLanguageId)) {
			fieldContext.put("disabled", Boolean.TRUE.toString());
		}

		fieldContext.put("fieldNamespace", StringUtil.randomId());

		boolean checkRequired = GetterUtil.getBoolean(
			request.getAttribute("checkRequired"), true);

		if (!checkRequired) {
			fieldContext.put("required", Boolean.FALSE.toString());
		}

		fieldsContext.put(name, fieldContext);

		return fieldContext;
	}

	protected String getFieldNamespace(
		String fieldDisplayValue, DDMFieldsCounter ddmFieldsCounter,
		int offset) {

		String[] fieldsDisplayValues = StringUtil.split(fieldDisplayValue);

		String fieldsDisplayValue = fieldsDisplayValues[offset];

		return StringUtil.extractLast(
			fieldsDisplayValue, DDMImpl.INSTANCE_SEPARATOR);
	}

	protected int getFieldOffset(
		String[] fieldsDisplayValues, String name, int index) {

		int offset = 0;

		for (; offset < fieldsDisplayValues.length; offset++) {
			if (name.equals(fieldsDisplayValues[offset])) {
				index--;

				if (index < 0) {
					break;
				}
			}
		}

		return offset;
	}

	protected Map<String, Map<String, Object>> getFieldsContext(
		HttpServletRequest request, HttpServletResponse response,
		String portletNamespace, String namespace) {

		String fieldsContextKey =
			portletNamespace + namespace + "fieldsContext";

		Map<String, Map<String, Object>> fieldsContext =
			(Map<String, Map<String, Object>>)request.getAttribute(
				fieldsContextKey);

		if (fieldsContext == null) {
			fieldsContext = new HashMap<>();

			request.setAttribute(fieldsContextKey, fieldsContext);
		}

		return fieldsContext;
	}

	protected DDMFieldsCounter getFieldsCounter(
		HttpServletRequest request, HttpServletResponse response, Fields fields,
		String portletNamespace, String namespace) {

		String fieldsCounterKey = portletNamespace + namespace + "fieldsCount";

		DDMFieldsCounter ddmFieldsCounter =
			(DDMFieldsCounter)request.getAttribute(fieldsCounterKey);

		if (ddmFieldsCounter == null) {
			ddmFieldsCounter = new DDMFieldsCounter();

			request.setAttribute(fieldsCounterKey, ddmFieldsCounter);
		}

		return ddmFieldsCounter;
	}

	protected String getFieldsDisplayValue(
		HttpServletRequest request, HttpServletResponse response,
		Fields fields) {

		String defaultFieldsDisplayValue = null;

		if (fields != null) {
			Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

			if (fieldsDisplayField != null) {
				defaultFieldsDisplayValue =
					(String)fieldsDisplayField.getValue();
			}
		}

		return ParamUtil.getString(
			request, DDMImpl.FIELDS_DISPLAY_NAME, defaultFieldsDisplayValue);
	}

	protected String[] getFieldsDisplayValues(String fieldDisplayValue) {
		List<String> fieldsDisplayValues = new ArrayList<>();

		for (String value : StringUtil.split(fieldDisplayValue)) {
			String fieldName = StringUtil.extractFirst(
				value, DDMImpl.INSTANCE_SEPARATOR);

			fieldsDisplayValues.add(fieldName);
		}

		return fieldsDisplayValues.toArray(
			new String[fieldsDisplayValues.size()]);
	}

	protected Map<String, Object> getFreeMarkerContext(
		HttpServletRequest request, HttpServletResponse response,
		String portletNamespace, String namespace,
		Element dynamicElementElement, Locale locale) {

		Map<String, Object> freeMarkerContext = new HashMap<>();

		Map<String, Object> fieldContext = getFieldContext(
			request, response, portletNamespace, namespace,
			dynamicElementElement, locale);

		Map<String, Object> parentFieldContext = new HashMap<>();

		Element parentElement = dynamicElementElement.getParent();

		if (parentElement != null) {
			parentFieldContext = getFieldContext(
				request, response, portletNamespace, namespace, parentElement,
				locale);
		}

		freeMarkerContext.put("fieldStructure", fieldContext);
		freeMarkerContext.put("namespace", namespace);
		freeMarkerContext.put("parentFieldStructure", parentFieldContext);
		freeMarkerContext.put("portletNamespace", portletNamespace);
		freeMarkerContext.put(
			"requestedLanguageDir", LanguageUtil.get(locale, "lang.dir"));
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
			HttpServletRequest request, HttpServletResponse response,
			Element element, String mode, boolean readOnly,
			Map<String, Object> freeMarkerContext)
		throws Exception {

		String fieldNamespace = element.attributeValue(
			"fieldNamespace", _DEFAULT_NAMESPACE);

		TemplateResource templateResource = _defaultTemplateResource;

		Map<String, Object> fieldStructure =
			(Map<String, Object>)freeMarkerContext.get("fieldStructure");

		boolean fieldReadOnly = GetterUtil.getBoolean(
			fieldStructure.get("readOnly"));

		if ((fieldReadOnly && Validator.isNotNull(mode) &&
			 StringUtil.equalsIgnoreCase(
				mode, DDMTemplateConstants.TEMPLATE_MODE_EDIT)) ||
			readOnly) {

			fieldNamespace = _DEFAULT_READ_ONLY_NAMESPACE;

			templateResource = _defaultReadOnlyTemplateResource;
		}

		String type = element.attributeValue("type");

		String templateName = StringUtil.replaceFirst(
			type, fieldNamespace.concat(StringPool.DASH), StringPool.BLANK);

		StringBundler resourcePath = new StringBundler(5);

		resourcePath.append(_TPL_PATH);
		resourcePath.append(StringUtil.toLowerCase(fieldNamespace));
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
			TemplateConstants.LANG_TYPE_FTL, templateResource, false);

		for (Map.Entry<String, Object> entry : freeMarkerContext.entrySet()) {
			template.put(entry.getKey(), entry.getValue());
		}

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(
				TemplateConstants.LANG_TYPE_FTL);

		templateManager.addTaglibApplication(
			template, "Application", request.getServletContext());
		templateManager.addTaglibFactory(
			template, "PortalJspTagLibs", request.getServletContext());
		templateManager.addTaglibRequest(
			template, "Request", request, response);

		return processFTL(request, response, template);
	}

	/**
	 * @see com.liferay.taglib.util.ThemeUtil#includeFTL
	 */
	protected String processFTL(
			HttpServletRequest request, HttpServletResponse response,
			Template template)
		throws Exception {

		template.prepare(request);

		Writer writer = new UnsyncStringWriter();

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

	private final TemplateResource _defaultReadOnlyTemplateResource;
	private final TemplateResource _defaultTemplateResource;

}