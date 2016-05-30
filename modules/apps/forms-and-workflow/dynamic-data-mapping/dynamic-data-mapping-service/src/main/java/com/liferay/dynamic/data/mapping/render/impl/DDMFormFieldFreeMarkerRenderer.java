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

package com.liferay.dynamic.data.mapping.render.impl;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.impl.DDMFieldsCounter;
import com.liferay.dynamic.data.mapping.util.impl.DDMImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.ClassLoaderTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Writer;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pablo Carvalho
 */
public class DDMFormFieldFreeMarkerRenderer implements DDMFormFieldRenderer {

	public DDMFormFieldFreeMarkerRenderer() {
		String defaultTemplateId = _TPL_PATH + "alloy/text.ftl";

		_defaultTemplateResource = getTemplateResource(defaultTemplateId);

		String defaultReadOnlyTemplateId = _TPL_PATH + "readonly/default.ftl";

		_defaultReadOnlyTemplateResource = getTemplateResource(
			defaultReadOnlyTemplateId);
	}

	@Override
	public String[] getSupportedDDMFormFieldTypes() {
		return _SUPPORTED_DDM_FORM_FIELD_TYPES;
	}

	@Override
	public String render(
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws PortalException {

		try {
			HttpServletRequest request =
				ddmFormFieldRenderingContext.getHttpServletRequest();
			HttpServletResponse response =
				ddmFormFieldRenderingContext.getHttpServletResponse();
			Fields fields = ddmFormFieldRenderingContext.getFields();
			String portletNamespace =
				ddmFormFieldRenderingContext.getPortletNamespace();
			String namespace = ddmFormFieldRenderingContext.getNamespace();
			String mode = ddmFormFieldRenderingContext.getMode();
			boolean readOnly = ddmFormFieldRenderingContext.isReadOnly();
			boolean showEmptyFieldLabel =
				ddmFormFieldRenderingContext.isShowEmptyFieldLabel();
			Locale locale = ddmFormFieldRenderingContext.getLocale();

			return getFieldHTML(
				request, response, ddmFormField, fields, null, portletNamespace,
				namespace, mode, readOnly, showEmptyFieldLabel, locale);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	protected void addLayoutProperties(
		DDMFormField ddmFormField, Map<String, Object> fieldContext,
		Locale locale) {

		LocalizedValue label = ddmFormField.getLabel();

		fieldContext.put("label", label.getString(locale));

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		fieldContext.put("predefinedValue", predefinedValue.getString(locale));

		LocalizedValue style = ddmFormField.getStyle();

		fieldContext.put("style", style.getString(locale));

		LocalizedValue tip = ddmFormField.getTip();

		fieldContext.put("tip", tip.getString(locale));
	}

	protected void addStructureProperties(
		DDMFormField ddmFormField, Map<String, Object> fieldContext) {

		fieldContext.put("dataType", ddmFormField.getDataType());
		fieldContext.put("indexType", ddmFormField.getIndexType());
		fieldContext.put(
			"localizable", Boolean.toString(ddmFormField.isLocalizable()));
		fieldContext.put(
			"multiple", Boolean.toString(ddmFormField.isMultiple()));
		fieldContext.put("name", ddmFormField.getName());
		fieldContext.put(
			"readOnly", Boolean.toString(ddmFormField.isReadOnly()));
		fieldContext.put(
			"repeatable", Boolean.toString(ddmFormField.isRepeatable()));
		fieldContext.put(
			"required", Boolean.toString(ddmFormField.isRequired()));
		fieldContext.put(
			"showLabel", Boolean.toString(ddmFormField.isShowLabel()));
		fieldContext.put("type", ddmFormField.getType());
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

	protected String getDDMFormFieldOptionHTML(
			HttpServletRequest request, HttpServletResponse response,
			DDMFormField ddmFormField, String mode, boolean readOnly,
			Locale locale, Map<String, Object> freeMarkerContext)
		throws Exception {

		StringBundler sb = new StringBundler();

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		for (String value : ddmFormFieldOptions.getOptionsValues()) {
			Map<String, Object> fieldStructure = new HashMap<>();

			fieldStructure.put("children", StringPool.BLANK);
			fieldStructure.put("fieldNamespace", StringUtil.randomId());

			LocalizedValue label = ddmFormFieldOptions.getOptionLabels(value);

			fieldStructure.put("label", label.getString(locale));

			fieldStructure.put("name", StringUtil.randomId());
			fieldStructure.put("value", value);

			freeMarkerContext.put("fieldStructure", fieldStructure);

			sb.append(
				processFTL(
					request, response, ddmFormField.getFieldNamespace(),
					"option", mode, readOnly, freeMarkerContext));
		}

		return sb.toString();
	}

	protected Map<String, Object> getFieldContext(
		HttpServletRequest request, HttpServletResponse response,
		String portletNamespace, String namespace, DDMFormField ddmFormField,
		Locale locale) {

		Map<String, Map<String, Object>> fieldsContext = getFieldsContext(
			request, response, portletNamespace, namespace);

		String name = ddmFormField.getName();

		Map<String, Object> fieldContext = fieldsContext.get(name);

		if (fieldContext != null) {
			return fieldContext;
		}

		DDMForm ddmForm = ddmFormField.getDDMForm();

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		Locale defaultLocale = ddmForm.getDefaultLocale();

		Locale structureLocale = locale;

		if (!availableLocales.contains(locale)) {
			structureLocale = defaultLocale;
		}

		fieldContext = new HashMap<>();

		addLayoutProperties(ddmFormField, fieldContext, structureLocale);

		addStructureProperties(ddmFormField, fieldContext);

		boolean checkRequired = GetterUtil.getBoolean(
			request.getAttribute("checkRequired"), true);

		if (!checkRequired) {
			fieldContext.put("required", Boolean.FALSE.toString());
		}

		fieldsContext.put(name, fieldContext);

		return fieldContext;
	}

	protected String getFieldHTML(
			HttpServletRequest request, HttpServletResponse response,
			DDMFormField ddmFormField, Fields fields,
			DDMFormField parentDDMFormField, String portletNamespace,
			String namespace, String mode, boolean readOnly,
			boolean showEmptyFieldLabel, Locale locale)
		throws Exception {

		Map<String, Object> freeMarkerContext = getFreeMarkerContext(
			request, response, portletNamespace, namespace, ddmFormField,
			parentDDMFormField, showEmptyFieldLabel, locale);

		if (fields != null) {
			freeMarkerContext.put("fields", fields);
		}

		Map<String, Object> fieldStructure =
			(Map<String, Object>)freeMarkerContext.get("fieldStructure");

		int fieldRepetition = 1;
		int offset = 0;

		DDMFieldsCounter ddmFieldsCounter = getFieldsCounter(
			request, response, fields, portletNamespace, namespace);

		String name = ddmFormField.getName();

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

			StringBundler childrenHTML = new StringBundler(2);

			childrenHTML.append(
				getHTML(
					request, response, ddmFormField.getNestedDDMFormFields(),
					fields, ddmFormField, portletNamespace, namespace, mode,
					readOnly, showEmptyFieldLabel, locale));

			if (Objects.equals(ddmFormField.getType(), "select") ||
				Objects.equals(ddmFormField.getType(), "radio")) {

				Map<String, Object> optionFreeMarkerContext = new HashMap<>(
					freeMarkerContext);

				optionFreeMarkerContext.put(
					"parentFieldStructure", fieldStructure);

				childrenHTML.append(
					getDDMFormFieldOptionHTML(
						request, response, ddmFormField, mode, readOnly, locale,
						optionFreeMarkerContext));
			}

			fieldStructure.put("children", childrenHTML.toString());

			sb.append(
				processFTL(
					request, response, ddmFormField.getFieldNamespace(),
					ddmFormField.getType(), mode, readOnly, freeMarkerContext));

			fieldRepetition--;
		}

		return sb.toString();
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
		String portletNamespace, String namespace, DDMFormField ddmFormField,
		DDMFormField parentDDMFormField, boolean showEmptyFieldLabel,
		Locale locale) {

		Map<String, Object> freeMarkerContext = new HashMap<>();

		Map<String, Object> fieldContext = getFieldContext(
			request, response, portletNamespace, namespace, ddmFormField,
			locale);

		Map<String, Object> parentFieldContext = new HashMap<>();

		if (parentDDMFormField != null) {
			parentFieldContext = getFieldContext(
				request, response, portletNamespace, namespace,
				parentDDMFormField, locale);
		}

		freeMarkerContext.put("fieldStructure", fieldContext);
		freeMarkerContext.put("namespace", namespace);
		freeMarkerContext.put("parentFieldStructure", parentFieldContext);
		freeMarkerContext.put("portletNamespace", portletNamespace);
		freeMarkerContext.put(
			"requestedLanguageDir",
			LanguageUtil.get(locale, LanguageConstants.KEY_DIR));
		freeMarkerContext.put("requestedLocale", locale);
		freeMarkerContext.put("showEmptyFieldLabel", showEmptyFieldLabel);

		return freeMarkerContext;
	}

	protected String getHTML(
			HttpServletRequest request, HttpServletResponse response,
			List<DDMFormField> ddmFormFields, Fields fields,
			DDMFormField parentDDMFormField, String portletNamespace,
			String namespace, String mode, boolean readOnly,
			boolean showEmptyFieldLabel, Locale locale)
		throws Exception {

		StringBundler sb = new StringBundler(ddmFormFields.size());

		for (DDMFormField ddmFormField : ddmFormFields) {
			sb.append(
				getFieldHTML(
					request, response, ddmFormField, fields, parentDDMFormField,
					portletNamespace, namespace, mode, readOnly,
					showEmptyFieldLabel, locale));
		}

		return sb.toString();
	}

	protected URL getResource(String name) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResource(name);
	}

	protected TemplateResource getTemplateResource(String resource) {
		Class<?> clazz = getClass();

		TemplateResource templateResource = new ClassLoaderTemplateResource(
			clazz.getClassLoader(), resource);

		return templateResource;
	}

	protected String processFTL(
			HttpServletRequest request, HttpServletResponse response,
			String fieldNamespace, String type, String mode, boolean readOnly,
			Map<String, Object> freeMarkerContext)
		throws Exception {

		if (Validator.isNull(fieldNamespace)) {
			fieldNamespace = _DEFAULT_NAMESPACE;
		}

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
			templateResource = getTemplateResource(resource);
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

		templateManager.addTaglibSupport(template, request, response);

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

	private static final String _DEFAULT_NAMESPACE = "alloy";

	private static final String _DEFAULT_READ_ONLY_NAMESPACE = "readonly";

	private static final String[] _SUPPORTED_DDM_FORM_FIELD_TYPES = {
		"checkbox", "ddm-date", "ddm-decimal", "ddm-documentlibrary",
		"ddm-geolocation", "ddm-image", "ddm-integer", "ddm-journal-article",
		"ddm-link-to-page", "ddm-number", "ddm-paragraph", "ddm-separator",
		"ddm-text-html", "fieldset", "option", "radio", "select", "text",
		"textarea"
	};

	private static final String _TPL_EXT = ".ftl";

	private static final String _TPL_PATH =
		"com/liferay/dynamic/data/mapping/dependencies/";

	private final TemplateResource _defaultReadOnlyTemplateResource;
	private final TemplateResource _defaultTemplateResource;

}