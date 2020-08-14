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

package com.liferay.dynamic.data.mapping.internal.render;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.internal.util.DDMFormFieldFreeMarkerRendererUtil;
import com.liferay.dynamic.data.mapping.internal.util.DDMImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMFieldsCounter;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.Editor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.constants.LanguageConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Writer;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
			return getFieldHTML(
				ddmFormFieldRenderingContext.getHttpServletRequest(),
				ddmFormFieldRenderingContext.getHttpServletResponse(),
				ddmFormField, ddmFormFieldRenderingContext.getFields(), null,
				ddmFormFieldRenderingContext.getPortletNamespace(),
				ddmFormFieldRenderingContext.getNamespace(),
				ddmFormFieldRenderingContext.getMode(),
				ddmFormFieldRenderingContext.isReadOnly(),
				ddmFormFieldRenderingContext.isShowEmptyFieldLabel(),
				ddmFormFieldRenderingContext.getLocale());
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	protected void addDDMFormFieldOptionHTML(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, DDMFormField ddmFormField,
			String mode, boolean readOnly,
			Map<String, Object> freeMarkerContext, StringBundler sb,
			String label, String value)
		throws Exception {

		freeMarkerContext.put(
			"fieldStructure",
			HashMapBuilder.<String, Object>put(
				"children", StringPool.BLANK
			).put(
				"fieldNamespace", StringUtil.randomId()
			).put(
				"label", label
			).put(
				"name", StringUtil.randomId()
			).put(
				"value", value
			).build());

		sb.append(
			processFTL(
				httpServletRequest, httpServletResponse,
				ddmFormField.getFieldNamespace(), "option", mode, readOnly,
				freeMarkerContext));
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, DDMFormField ddmFormField,
			String mode, boolean readOnly, Locale locale,
			Map<String, Object> freeMarkerContext)
		throws Exception {

		StringBundler sb = new StringBundler();

		if (Objects.equals(ddmFormField.getType(), "select")) {
			addDDMFormFieldOptionHTML(
				httpServletRequest, httpServletResponse, ddmFormField, mode,
				readOnly, freeMarkerContext, sb, StringPool.BLANK,
				StringPool.BLANK);
		}

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		for (String value : ddmFormFieldOptions.getOptionsValues()) {
			if (value.equals(StringPool.BLANK)) {
				continue;
			}

			LocalizedValue label = ddmFormFieldOptions.getOptionLabels(value);

			addDDMFormFieldOptionHTML(
				httpServletRequest, httpServletResponse, ddmFormField, mode,
				readOnly, freeMarkerContext, sb,
				label.getString(
					_getPreferredLocale(
						httpServletRequest, ddmFormField, locale)),
				value);
		}

		return sb.toString();
	}

	protected Map<String, Object> getFieldContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String portletNamespace,
		String namespace, DDMFormField ddmFormField, Locale locale) {

		Map<String, Map<String, Object>> fieldsContext = getFieldsContext(
			httpServletRequest, httpServletResponse, portletNamespace,
			namespace);

		String name = ddmFormField.getName();

		Map<String, Object> fieldContext = fieldsContext.get(name);

		if (fieldContext != null) {
			return fieldContext;
		}

		fieldContext = new HashMap<>();

		addLayoutProperties(
			ddmFormField, fieldContext,
			_getPreferredLocale(httpServletRequest, ddmFormField, locale));

		addStructureProperties(ddmFormField, fieldContext);

		boolean checkRequired = GetterUtil.getBoolean(
			httpServletRequest.getAttribute("checkRequired"), true);

		if (!checkRequired) {
			fieldContext.put("required", Boolean.FALSE.toString());
		}

		fieldsContext.put(name, fieldContext);

		return fieldContext;
	}

	protected String getFieldHTML(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, DDMFormField ddmFormField,
			Fields fields, DDMFormField parentDDMFormField,
			String portletNamespace, String namespace, String mode,
			boolean readOnly, boolean showEmptyFieldLabel, Locale locale)
		throws Exception {

		Map<String, Object> freeMarkerContext = getFreeMarkerContext(
			httpServletRequest, httpServletResponse, portletNamespace,
			namespace, ddmFormField, parentDDMFormField, showEmptyFieldLabel,
			locale);

		if (fields != null) {
			freeMarkerContext.put("fields", fields);
		}

		Map<String, Object> fieldStructure =
			(Map<String, Object>)freeMarkerContext.get("fieldStructure");

		int fieldRepetition = 1;
		int offset = 0;

		DDMFieldsCounter ddmFieldsCounter = getFieldsCounter(
			httpServletRequest, httpServletResponse, fields, portletNamespace,
			namespace);

		String name = ddmFormField.getName();

		String fieldsDisplayValue = getFieldsDisplayValue(
			httpServletRequest, httpServletResponse, fields);

		String[] fieldsDisplayValues = getFieldsDisplayValues(
			fieldsDisplayValue);

		boolean fieldDisplayable = ArrayUtil.contains(
			fieldsDisplayValues, name);

		if (fieldDisplayable) {
			offset = getFieldOffset(
				fieldsDisplayValues, name, ddmFieldsCounter.get(name));

			if (offset == fieldsDisplayValues.length) {
				return StringPool.BLANK;
			}

			Map<String, Object> parentFieldStructure =
				(Map<String, Object>)freeMarkerContext.get(
					"parentFieldStructure");

			String parentFieldName = (String)parentFieldStructure.get("name");

			fieldRepetition = countFieldRepetition(
				fieldsDisplayValues, parentFieldName, offset);
		}

		StringBundler sb = new StringBundler(fieldRepetition);

		while (fieldRepetition > 0) {
			offset = getFieldOffset(
				fieldsDisplayValues, name, ddmFieldsCounter.get(name));

			String fieldNamespace = StringUtil.randomId(8);

			if (fieldDisplayable) {
				fieldNamespace = getFieldNamespace(
					fieldsDisplayValue, ddmFieldsCounter, offset);
			}

			fieldStructure.put("fieldNamespace", fieldNamespace);
			fieldStructure.put("valueIndex", ddmFieldsCounter.get(name));

			if (fieldDisplayable) {
				ddmFieldsCounter.incrementKey(name);
			}

			StringBundler childrenHTMLSB = new StringBundler(2);

			childrenHTMLSB.append(
				getHTML(
					httpServletRequest, httpServletResponse,
					ddmFormField.getNestedDDMFormFields(), fields, ddmFormField,
					portletNamespace, namespace, mode, readOnly,
					showEmptyFieldLabel, locale));

			if (Objects.equals(ddmFormField.getType(), "select") ||
				Objects.equals(ddmFormField.getType(), "radio")) {

				Map<String, Object> optionFreeMarkerContext = new HashMap<>(
					freeMarkerContext);

				optionFreeMarkerContext.put(
					"parentFieldStructure", fieldStructure);

				childrenHTMLSB.append(
					getDDMFormFieldOptionHTML(
						httpServletRequest, httpServletResponse, ddmFormField,
						mode, readOnly, locale, optionFreeMarkerContext));
			}

			fieldStructure.put("children", childrenHTMLSB.toString());

			sb.append(
				processFTL(
					httpServletRequest, httpServletResponse,
					ddmFormField.getFieldNamespace(), ddmFormField.getType(),
					mode, readOnly, freeMarkerContext));

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
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String portletNamespace,
		String namespace) {

		String fieldsContextKey =
			portletNamespace + namespace + "fieldsContext";

		Map<String, Map<String, Object>> fieldsContext =
			(Map<String, Map<String, Object>>)httpServletRequest.getAttribute(
				fieldsContextKey);

		if (fieldsContext == null) {
			fieldsContext = new HashMap<>();

			httpServletRequest.setAttribute(fieldsContextKey, fieldsContext);
		}

		return fieldsContext;
	}

	protected DDMFieldsCounter getFieldsCounter(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Fields fields,
		String portletNamespace, String namespace) {

		String fieldsCounterKey = portletNamespace + namespace + "fieldsCount";

		DDMFieldsCounter ddmFieldsCounter =
			(DDMFieldsCounter)httpServletRequest.getAttribute(fieldsCounterKey);

		if (ddmFieldsCounter == null) {
			ddmFieldsCounter = new DDMFieldsCounter();

			httpServletRequest.setAttribute(fieldsCounterKey, ddmFieldsCounter);
		}

		return ddmFieldsCounter;
	}

	protected String getFieldsDisplayValue(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Fields fields) {

		String defaultFieldsDisplayValue = null;

		if (fields != null) {
			Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

			if (fieldsDisplayField != null) {
				defaultFieldsDisplayValue =
					(String)fieldsDisplayField.getValue();
			}
		}

		return ParamUtil.getString(
			httpServletRequest, DDMImpl.FIELDS_DISPLAY_NAME,
			defaultFieldsDisplayValue);
	}

	protected String[] getFieldsDisplayValues(String fieldDisplayValue) {
		List<String> fieldsDisplayValues = new ArrayList<>();

		for (String value : StringUtil.split(fieldDisplayValue)) {
			String fieldName = StringUtil.extractFirst(
				value, DDMImpl.INSTANCE_SEPARATOR);

			fieldsDisplayValues.add(fieldName);
		}

		return fieldsDisplayValues.toArray(new String[0]);
	}

	protected Map<String, Object> getFreeMarkerContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String portletNamespace,
		String namespace, DDMFormField ddmFormField,
		DDMFormField parentDDMFormField, boolean showEmptyFieldLabel,
		Locale locale) {

		Map<String, Object> fieldContext = getFieldContext(
			httpServletRequest, httpServletResponse, portletNamespace,
			namespace, ddmFormField, locale);

		Map<String, Object> parentFieldContext = new HashMap<>();

		if (parentDDMFormField != null) {
			parentFieldContext = getFieldContext(
				httpServletRequest, httpServletResponse, portletNamespace,
				namespace, parentDDMFormField, locale);
		}

		Map<String, Object> freeMarkerContext =
			HashMapBuilder.<String, Object>put(
				"ddmPortletId", DDMPortletKeys.DYNAMIC_DATA_MAPPING
			).put(
				"editorName",
				() -> {
					Editor editor =
						DDMFormFieldFreeMarkerRendererUtil.getEditor(
							httpServletRequest);

					return editor.getName();
				}
			).put(
				"fieldStructure", fieldContext
			).build();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			String itemSelectorAuthToken = AuthTokenUtil.getToken(
				httpServletRequest,
				PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId()),
				PortletKeys.ITEM_SELECTOR);

			freeMarkerContext.put(
				"itemSelectorAuthToken", itemSelectorAuthToken);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to generate item selector auth token ",
				portalException);
		}

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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			List<DDMFormField> ddmFormFields, Fields fields,
			DDMFormField parentDDMFormField, String portletNamespace,
			String namespace, String mode, boolean readOnly,
			boolean showEmptyFieldLabel, Locale locale)
		throws Exception {

		StringBundler sb = new StringBundler(ddmFormFields.size());

		for (DDMFormField ddmFormField : ddmFormFields) {
			sb.append(
				getFieldHTML(
					httpServletRequest, httpServletResponse, ddmFormField,
					fields, parentDDMFormField, portletNamespace, namespace,
					mode, readOnly, showEmptyFieldLabel, locale));
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

		try {
			return TemplateResourceLoaderUtil.getTemplateResource(
				TemplateConstants.LANG_TYPE_FTL,
				StringBundler.concat(
					ClassLoaderPool.getContextName(clazz.getClassLoader()),
					TemplateConstants.CLASS_LOADER_SEPARATOR, resource));
		}
		catch (TemplateException templateException) {
			_log.error(
				"Unable to find template resource " + resource,
				templateException);
		}

		return null;
	}

	protected String processFTL(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String fieldNamespace,
			String type, String mode, boolean readOnly,
			Map<String, Object> freeMarkerContext)
		throws Exception {

		if (Validator.isNull(fieldNamespace)) {
			fieldNamespace = _DEFAULT_NAMESPACE;
		}

		TemplateResource templateResource = getTemplateResource(
			_TPL_PATH + "alloy/text.ftl");

		Map<String, Object> fieldStructure =
			(Map<String, Object>)freeMarkerContext.get("fieldStructure");

		boolean fieldReadOnly = GetterUtil.getBoolean(
			fieldStructure.get("readOnly"));

		if ((fieldReadOnly && Validator.isNotNull(mode) &&
			 StringUtil.equalsIgnoreCase(
				 mode, DDMTemplateConstants.TEMPLATE_MODE_EDIT)) ||
			readOnly) {

			fieldNamespace = _DEFAULT_READ_ONLY_NAMESPACE;

			templateResource = getTemplateResource(
				_TPL_PATH + "readonly/default.ftl");
		}

		String templateName = StringUtil.replaceFirst(
			type, fieldNamespace.concat(StringPool.DASH), StringPool.BLANK);

		StringBundler sb = new StringBundler(5);

		sb.append(_TPL_PATH);
		sb.append(StringUtil.toLowerCase(fieldNamespace));
		sb.append(CharPool.SLASH);
		sb.append(templateName);
		sb.append(_TPL_EXT);

		String resource = sb.toString();

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

		template.prepareTaglib(httpServletRequest, httpServletResponse);

		return processFTL(httpServletRequest, httpServletResponse, template);
	}

	/**
	 * @see com.liferay.taglib.util.ThemeUtil#includeFTL
	 */
	protected String processFTL(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Template template)
		throws Exception {

		template.prepare(httpServletRequest);

		Writer writer = new UnsyncStringWriter();

		template.processTemplate(writer);

		return writer.toString();
	}

	private Locale _getPreferredLocale(
		HttpServletRequest httpServletRequest, DDMFormField ddmFormField,
		Locale locale) {

		DDMForm ddmForm = ddmFormField.getDDMForm();

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		if (availableLocales.contains(locale)) {
			return locale;
		}

		if (availableLocales.contains(ddmForm.getDefaultLocale())) {
			return ddmForm.getDefaultLocale();
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (availableLocales.contains(themeDisplay.getSiteDefaultLocale())) {
			return themeDisplay.getSiteDefaultLocale();
		}

		Iterator<Locale> iterator = availableLocales.iterator();

		return iterator.next();
	}

	private static final String _DEFAULT_NAMESPACE = "alloy";

	private static final String _DEFAULT_READ_ONLY_NAMESPACE = "readonly";

	private static final String[] _SUPPORTED_DDM_FORM_FIELD_TYPES = {
		"checkbox", "ddm-color", "ddm-date", "ddm-decimal",
		"ddm-documentlibrary", "ddm-geolocation", "ddm-image", "ddm-integer",
		"ddm-journal-article", "ddm-link-to-page", "ddm-number",
		"ddm-paragraph", "ddm-separator", "ddm-text-html", "fieldset", "option",
		"radio", "select", "text", "textarea"
	};

	private static final String _TPL_EXT = ".ftl";

	private static final String _TPL_PATH =
		"com/liferay/dynamic/data/mapping/service/dependencies/";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormFieldFreeMarkerRenderer.class);

}