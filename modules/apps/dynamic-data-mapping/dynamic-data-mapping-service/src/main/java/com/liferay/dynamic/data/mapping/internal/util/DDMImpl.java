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

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.dynamic.data.mapping.util.comparator.StructureIdComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.TemplateIdComparator;
import com.liferay.dynamic.data.mapping.util.comparator.TemplateModifiedDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.Serializable;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Eduardo García
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDM.class)
public class DDMImpl implements DDM {

	public static final String FIELDS_DISPLAY_NAME = "_fieldsDisplay";

	public static final String INSTANCE_SEPARATOR = "_INSTANCE_";

	public static final String TYPE_CHECKBOX = "checkbox";

	public static final String TYPE_DDM_DATE = "ddm-date";

	public static final String TYPE_DDM_DOCUMENTLIBRARY = "ddm-documentlibrary";

	public static final String TYPE_DDM_IMAGE = "ddm-image";

	public static final String TYPE_DDM_LINK_TO_PAGE = "ddm-link-to-page";

	public static final String TYPE_DDM_TEXT_HTML = "ddm-text-html";

	public static final String TYPE_RADIO = "radio";

	public static final String TYPE_SELECT = "select";

	@Override
	public DDMForm getDDMForm(long classNameId, long classPK)
		throws PortalException {

		if ((classNameId <= 0) || (classPK <= 0)) {
			return null;
		}

		long ddmStructureClassNameId = _portal.getClassNameId(
			DDMStructure.class);
		long ddmTemplateClassNameId = _portal.getClassNameId(DDMTemplate.class);

		if (classNameId == ddmStructureClassNameId) {
			DDMStructure structure = DDMStructureLocalServiceUtil.getStructure(
				classPK);

			return structure.getFullHierarchyDDMForm();
		}
		else if (classNameId == ddmTemplateClassNameId) {
			DDMTemplate template = DDMTemplateLocalServiceUtil.getTemplate(
				classPK);

			return getDDMForm(template.getScript());
		}

		return null;
	}

	@Override
	public DDMForm getDDMForm(PortletRequest portletRequest)
		throws PortalException {

		String definition = ParamUtil.getString(portletRequest, "definition");

		return getDDMForm(definition);
	}

	@Override
	public DDMForm getDDMForm(String content) throws PortalException {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	@Override
	public JSONArray getDDMFormFieldsJSONArray(
		DDMStructure ddmStructure, String script) {

		DDMForm ddmForm = null;

		if (ddmStructure != null) {
			ddmForm = ddmStructure.getFullHierarchyDDMForm();
		}

		return getDDMFormFieldsJSONArray(ddmForm, script);
	}

	@Override
	public JSONArray getDDMFormFieldsJSONArray(
		DDMStructureVersion ddmStructureVersion, String script) {

		DDMForm ddmForm = null;

		if (ddmStructureVersion != null) {
			ddmForm = ddmStructureVersion.getDDMForm();
		}

		return getDDMFormFieldsJSONArray(ddmForm, script);
	}

	@Override
	public String getDDMFormJSONString(DDMForm ddmForm) {
		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_jsonDDMFormSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	@Override
	public DDMFormValues getDDMFormValues(
		DDMForm ddmForm, String serializedJSONDDMFormValues) {

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				serializedJSONDDMFormValues, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_jsonDDMFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	@Override
	public DDMFormValues getDDMFormValues(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		Fields fields = getFields(
			ddmStructure.getStructureId(), fieldNamespace, serviceContext);

		return _fieldsToDDMFormValuesConverter.convert(ddmStructure, fields);
	}

	@Override
	public String getDDMFormValuesJSONString(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_jsonDDMFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	@Override
	public DDMFormLayout getDefaultDDMFormLayout(DDMForm ddmForm) {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		Locale defaultLocale = ddmForm.getDefaultLocale();

		ddmFormLayout.setDefaultLocale(defaultLocale);

		ddmFormLayout.setPaginationMode(DDMFormLayout.SINGLE_PAGE_MODE);

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		LocalizedValue title = getDefaultDDMFormPageTitle(defaultLocale);

		ddmFormLayoutPage.setTitle(title);

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			ddmFormLayoutPage.addDDMFormLayoutRow(
				getDefaultDDMFormLayoutRow(ddmFormField));
		}

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);

		return ddmFormLayout;
	}

	@Override
	public Serializable getDisplayFieldValue(
			ThemeDisplay themeDisplay, Serializable fieldValue, String type)
		throws Exception {

		if (type.equals(DDMImpl.TYPE_DDM_DATE)) {
			fieldValue = DateUtil.formatDate(
				"yyyy-MM-dd", fieldValue.toString(), themeDisplay.getLocale());
		}
		else if (type.equals(DDMImpl.TYPE_CHECKBOX)) {
			Boolean valueBoolean = (Boolean)fieldValue;

			if (valueBoolean) {
				fieldValue = LanguageUtil.get(themeDisplay.getLocale(), "yes");
			}
			else {
				fieldValue = LanguageUtil.get(themeDisplay.getLocale(), "no");
			}
		}
		else if (type.equals(DDMImpl.TYPE_DDM_DOCUMENTLIBRARY)) {
			if (Validator.isNull(fieldValue)) {
				return StringPool.BLANK;
			}

			String valueString = String.valueOf(fieldValue);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			FileEntry fileEntry =
				_dlAppLocalService.getFileEntryByUuidAndGroupId(uuid, groupId);

			fieldValue = _dlURLHelper.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, true);
		}
		else if (type.equals(DDMImpl.TYPE_DDM_LINK_TO_PAGE)) {
			if (Validator.isNull(fieldValue)) {
				return StringPool.BLANK;
			}

			String valueString = String.valueOf(fieldValue);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long groupId = jsonObject.getLong("groupId");
			boolean privateLayout = jsonObject.getBoolean("privateLayout");
			long layoutId = jsonObject.getLong("layoutId");

			Layout layout = _layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);

			fieldValue = _portal.getLayoutFriendlyURL(layout, themeDisplay);
		}
		else if (type.equals(DDMImpl.TYPE_SELECT)) {
			String valueString = String.valueOf(fieldValue);

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(valueString);

			String[] stringArray = ArrayUtil.toStringArray(jsonArray);

			fieldValue = stringArray[0];
		}

		return fieldValue;
	}

	@Override
	public Fields getFields(long ddmStructureId, DDMFormValues ddmFormValues)
		throws PortalException {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		return _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, ddmFormValues);
	}

	@Override
	public Fields getFields(
			long ddmStructureId, long ddmTemplateId,
			ServiceContext serviceContext)
		throws PortalException {

		return getFields(
			ddmStructureId, ddmTemplateId, StringPool.BLANK, serviceContext);
	}

	@Override
	public Fields getFields(
			long ddmStructureId, long ddmTemplateId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructure ddmStructure = getDDMStructure(
			ddmStructureId, ddmTemplateId);

		Set<String> fieldNames = ddmStructure.getFieldNames();

		boolean translating = true;

		String defaultLanguageId = (String)serviceContext.getAttribute(
			"defaultLanguageId");
		String toLanguageId = (String)serviceContext.getAttribute(
			"toLanguageId");

		if (Validator.isNull(toLanguageId) ||
			Objects.equals(defaultLanguageId, toLanguageId)) {

			translating = false;
		}

		Fields fields = new Fields();

		for (String fieldName : fieldNames) {
			boolean localizable = GetterUtil.getBoolean(
				ddmStructure.getFieldProperty(fieldName, "localizable"), true);

			if (!localizable && translating &&
				!fieldName.startsWith(StringPool.UNDERLINE)) {

				continue;
			}

			List<Serializable> fieldValues = getFieldValues(
				ddmStructure, fieldName, fieldNamespace, serviceContext);

			if ((fieldValues == null) || fieldValues.isEmpty()) {
				continue;
			}

			Field field = createField(
				ddmStructure, fieldName, fieldValues, serviceContext);

			fields.put(field);
		}

		return fields;
	}

	@Override
	public Fields getFields(long ddmStructureId, ServiceContext serviceContext)
		throws PortalException {

		String serializedDDMFormValues = GetterUtil.getString(
			serviceContext.getAttribute("ddmFormValues"));

		if (Validator.isNotNull(serializedDDMFormValues)) {
			return getFields(ddmStructureId, serializedDDMFormValues);
		}

		return getFields(ddmStructureId, 0, serviceContext);
	}

	@Override
	public Fields getFields(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		String serializedDDMFormValues = GetterUtil.getString(
			serviceContext.getAttribute(fieldNamespace + "ddmFormValues"));

		if (Validator.isNotNull(serializedDDMFormValues)) {
			return getFields(ddmStructureId, serializedDDMFormValues);
		}

		return getFields(ddmStructureId, 0, fieldNamespace, serviceContext);
	}

	@Override
	public Serializable getIndexedFieldValue(
			Serializable fieldValue, String type)
		throws Exception {

		if (fieldValue instanceof Date) {
			Date valueDate = (Date)fieldValue;

			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyyMMddHHmmss");

			fieldValue = dateFormat.format(valueDate);
		}
		else if (type.equals(DDMImpl.TYPE_SELECT)) {
			String valueString = (String)fieldValue;

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(valueString);

			String[] stringArray = ArrayUtil.toStringArray(jsonArray);

			fieldValue = stringArray[0];
		}

		return fieldValue;
	}

	@Override
	public OrderByComparator<DDMStructure> getStructureOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDMStructure> orderByComparator = null;

		if (orderByCol.equals("id")) {
			orderByComparator = new StructureIdComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new StructureModifiedDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	@Override
	public OrderByComparator<DDMTemplate> getTemplateOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDMTemplate> orderByComparator = null;

		if (orderByCol.equals("id")) {
			orderByComparator = new TemplateIdComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new TemplateModifiedDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	@Override
	public Fields mergeFields(Fields newFields, Fields existingFields) {
		String[] newFieldsDisplayValues = splitFieldsDisplayValue(
			newFields.get(DDMImpl.FIELDS_DISPLAY_NAME));

		String[] existingFieldsDisplayValues = splitFieldsDisplayValue(
			existingFields.get(DDMImpl.FIELDS_DISPLAY_NAME));

		Iterator<Field> itr = newFields.iterator(true);

		while (itr.hasNext()) {
			Field newField = itr.next();

			Field existingField = existingFields.get(newField.getName());

			if (existingField == null) {
				existingFields.put(newField);

				continue;
			}

			if (newField.isPrivate()) {
				String[] existingFieldValues = splitFieldsDisplayValue(
					existingField);

				String[] newFieldValues = splitFieldsDisplayValue(newField);

				if (newFieldValues.length > existingFieldValues.length) {
					existingFields.put(newField);
				}

				continue;
			}

			existingField.setDefaultLocale(newField.getDefaultLocale());

			Map<Locale, List<Serializable>> mergedFieldValuesMap =
				getMergedFieldValuesMap(
					newField, newFieldsDisplayValues, existingField,
					existingFieldsDisplayValues);

			existingField.setValuesMap(mergedFieldValuesMap);
		}

		return existingFields;
	}

	@Override
	public DDMForm updateDDMFormDefaultLocale(
		DDMForm ddmForm, Locale newDefaultLocale) {

		DDMForm ddmFormCopy = new DDMForm(ddmForm);

		Locale defautLocale = ddmForm.getDefaultLocale();

		if (defautLocale.equals(newDefaultLocale)) {
			return ddmFormCopy;
		}

		ddmFormCopy.addAvailableLocale(newDefaultLocale);
		ddmFormCopy.setDefaultLocale(newDefaultLocale);

		updateDDMFormFieldsDefaultLocale(
			ddmFormCopy.getDDMFormFields(), newDefaultLocale);

		return ddmFormCopy;
	}

	protected void addDDMFormFieldLocalizedProperties(
		JSONObject jsonObject, DDMFormField ddmFormField, Locale locale,
		Locale defaultLocale) {

		addDDMFormFieldLocalizedProperty(
			jsonObject, "label", ddmFormField.getLabel(), locale, defaultLocale,
			ddmFormField.getType());
		addDDMFormFieldLocalizedProperty(
			jsonObject, "predefinedValue", ddmFormField.getPredefinedValue(),
			locale, defaultLocale, ddmFormField.getType());
		addDDMFormFieldLocalizedProperty(
			jsonObject, "tip", ddmFormField.getTip(), locale, defaultLocale,
			ddmFormField.getType());
	}

	protected void addDDMFormFieldLocalizedProperty(
		JSONObject jsonObject, String propertyName,
		LocalizedValue localizedValue, Locale locale, Locale defaultLocale,
		String type) {

		String propertyValue = localizedValue.getString(locale);

		if (Validator.isNull(propertyValue)) {
			propertyValue = localizedValue.getString(defaultLocale);
		}

		if (type.equals(DDMImpl.TYPE_SELECT) &&
			propertyName.equals("predefinedValue")) {

			try {
				jsonObject.put(
					propertyName,
					JSONFactoryUtil.createJSONArray(propertyValue));
			}
			catch (Exception e) {
			}

			return;
		}

		jsonObject.put(propertyName, propertyValue);
	}

	protected void addDDMFormFieldOptions(
		JSONObject jsonObject, DDMFormField ddmFormField,
		Set<Locale> availableLocales, Locale defaultLocale) {

		String type = ddmFormField.getType();

		if (!(type.equals(DDMImpl.TYPE_RADIO) ||
			  type.equals(DDMImpl.TYPE_SELECT))) {

			return;
		}

		String fieldName = ddmFormField.getName();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			String name = fieldName.concat(StringUtil.randomString());

			JSONObject optionJSONObject = JSONUtil.put(
				"id", name
			).put(
				"name", name
			).put(
				"type", "option"
			).put(
				"value", optionValue
			);

			addDDMFormFieldLocalizedProperty(
				optionJSONObject, "label",
				ddmFormFieldOptions.getOptionLabels(optionValue), defaultLocale,
				defaultLocale, "option");

			JSONObject localizationMapJSONObject =
				JSONFactoryUtil.createJSONObject();

			for (Locale availableLocale : availableLocales) {
				JSONObject localeMap = JSONFactoryUtil.createJSONObject();

				addDDMFormFieldLocalizedProperty(
					localeMap, "label",
					ddmFormFieldOptions.getOptionLabels(optionValue),
					availableLocale, defaultLocale, "option");

				localizationMapJSONObject.put(
					LocaleUtil.toLanguageId(availableLocale), localeMap);
			}

			optionJSONObject.put("localizationMap", localizationMapJSONObject);

			jsonArray.put(optionJSONObject);
		}

		jsonObject.put("options", jsonArray);
	}

	protected int countFieldRepetition(
		String[] fieldsDisplayValues, String fieldName) {

		int count = 0;

		for (String fieldsDisplayValue : fieldsDisplayValues) {
			String prefix = StringUtil.extractFirst(
				fieldsDisplayValue, INSTANCE_SEPARATOR);

			if (prefix.equals(fieldName)) {
				count++;
			}
		}

		return count;
	}

	protected Field createField(
		DDMStructure ddmStructure, String fieldName,
		List<Serializable> fieldValues, ServiceContext serviceContext) {

		Field field = new Field();

		field.setDDMStructureId(ddmStructure.getStructureId());

		String languageId = GetterUtil.getString(
			serviceContext.getAttribute("languageId"),
			serviceContext.getLanguageId());

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		String defaultLanguageId = GetterUtil.getString(
			serviceContext.getAttribute("defaultLanguageId"));

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		if (fieldName.startsWith(StringPool.UNDERLINE)) {
			locale = LocaleUtil.getSiteDefault();

			defaultLocale = LocaleUtil.getSiteDefault();
		}

		field.setDefaultLocale(defaultLocale);

		field.setName(fieldName);
		field.setValues(locale, fieldValues);

		return field;
	}

	protected JSONArray getDDMFormFieldsJSONArray(
		DDMForm ddmForm, String script) {

		JSONArray ddmFormFieldsJSONArray = null;

		if (ddmForm != null) {
			ddmFormFieldsJSONArray = getDDMFormFieldsJSONArray(
				ddmForm.getDDMFormFields(), ddmForm.getAvailableLocales(),
				ddmForm.getDefaultLocale());
		}
		else if (Validator.isNotNull(script)) {
			try {
				DDMForm scriptDDMForm = getDDMForm(script);

				ddmFormFieldsJSONArray = getDDMFormFieldsJSONArray(
					scriptDDMForm.getDDMFormFields(),
					scriptDDMForm.getAvailableLocales(),
					scriptDDMForm.getDefaultLocale());
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe, pe);
				}
			}
		}

		return ddmFormFieldsJSONArray;
	}

	protected JSONArray getDDMFormFieldsJSONArray(
		List<DDMFormField> ddmFormFields, Set<Locale> availableLocales,
		Locale defaultLocale) {

		JSONArray ddmFormFieldsJSONArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormField ddmFormField : ddmFormFields) {
			JSONObject jsonObject = JSONUtil.put(
				"dataType", ddmFormField.getDataType()
			).put(
				"id", ddmFormField.getName()
			).put(
				"indexType", ddmFormField.getIndexType()
			).put(
				"localizable", ddmFormField.isLocalizable()
			).put(
				"multiple", ddmFormField.isMultiple()
			).put(
				"name", ddmFormField.getName()
			).put(
				"readOnly", ddmFormField.isReadOnly()
			).put(
				"repeatable", ddmFormField.isRepeatable()
			).put(
				"required", ddmFormField.isRequired()
			).put(
				"showLabel", ddmFormField.isShowLabel()
			).put(
				"type", ddmFormField.getType()
			);

			addDDMFormFieldLocalizedProperties(
				jsonObject, ddmFormField, defaultLocale, defaultLocale);

			addDDMFormFieldOptions(
				jsonObject, ddmFormField, availableLocales, defaultLocale);

			JSONObject localizationMapJSONObject =
				JSONFactoryUtil.createJSONObject();

			for (Locale availableLocale : availableLocales) {
				JSONObject localeMap = JSONFactoryUtil.createJSONObject();

				addDDMFormFieldLocalizedProperties(
					localeMap, ddmFormField, availableLocale, defaultLocale);

				localizationMapJSONObject.put(
					LocaleUtil.toLanguageId(availableLocale), localeMap);
			}

			jsonObject.put(
				"fields",
				getDDMFormFieldsJSONArray(
					ddmFormField.getNestedDDMFormFields(), availableLocales,
					defaultLocale)
			).put(
				"localizationMap", localizationMapJSONObject
			);

			ddmFormFieldsJSONArray.put(jsonObject);
		}

		return ddmFormFieldsJSONArray;
	}

	protected DDMStructure getDDMStructure(
			long ddmStructureId, long ddmTemplateId)
		throws PortalException {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
			ddmTemplateId);

		if (ddmTemplate != null) {

			// Clone ddmStructure to make sure changes are never persisted

			ddmStructure = (DDMStructure)ddmStructure.clone();

			ddmStructure.setDefinition(ddmTemplate.getScript());
		}

		return ddmStructure;
	}

	protected DDMFormLayoutRow getDefaultDDMFormLayoutRow(
		DDMFormField ddmFormField) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.addDDMFormLayoutColumn(
			new DDMFormLayoutColumn(
				DDMFormLayoutColumn.FULL, ddmFormField.getName()));

		return ddmFormLayoutRow;
	}

	protected LocalizedValue getDefaultDDMFormPageTitle(Locale defaultLocale) {
		LocalizedValue title = new LocalizedValue(defaultLocale);

		title.addString(defaultLocale, StringPool.BLANK);

		return title;
	}

	protected int getExistingFieldValueIndex(
		String[] newFieldsDisplayValues, String[] existingFieldsDisplayValues,
		String fieldName, int index) {

		String instanceId = getFieldIntanceId(
			newFieldsDisplayValues, fieldName, index);

		return getFieldValueIndex(
			existingFieldsDisplayValues, fieldName, instanceId);
	}

	protected String getFieldIntanceId(
		String[] fieldsDisplayValues, String fieldName, int index) {

		String prefix = fieldName.concat(INSTANCE_SEPARATOR);

		for (String fieldsDisplayValue : fieldsDisplayValues) {
			if (fieldsDisplayValue.startsWith(prefix)) {
				index--;

				if (index < 0) {
					return StringUtil.extractLast(
						fieldsDisplayValue, DDMImpl.INSTANCE_SEPARATOR);
				}
			}
		}

		return null;
	}

	protected List<String> getFieldNames(
		String fieldNamespace, String fieldName,
		ServiceContext serviceContext) {

		String[] fieldsDisplayValues = StringUtil.split(
			(String)serviceContext.getAttribute(
				fieldNamespace + FIELDS_DISPLAY_NAME));

		List<String> privateFieldNames = ListUtil.fromArray(
			FIELDS_DISPLAY_NAME);

		List<String> fieldNames = new ArrayList<>();

		if ((fieldsDisplayValues.length == 0) ||
			privateFieldNames.contains(fieldName)) {

			fieldNames.add(fieldNamespace + fieldName);
		}
		else {
			for (String namespacedFieldName : fieldsDisplayValues) {
				String fieldNameValue = StringUtil.extractFirst(
					namespacedFieldName, INSTANCE_SEPARATOR);

				if (fieldNameValue.equals(fieldName)) {
					fieldNames.add(fieldNamespace + namespacedFieldName);
				}
			}
		}

		return fieldNames;
	}

	protected Fields getFields(
			long ddmStructureId, String serializedDDMFormValues)
		throws PortalException {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		DDMFormValues ddmFormValues = getDDMFormValues(
			ddmStructure.getFullHierarchyDDMForm(), serializedDDMFormValues);

		return _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, ddmFormValues);
	}

	protected int getFieldValueIndex(
		String[] fieldsDisplayValues, String fieldName, String instanceId) {

		if (Validator.isNull(instanceId)) {
			return -1;
		}

		int offset = 0;

		String prefix = fieldName.concat(INSTANCE_SEPARATOR);

		for (String fieldsDisplayValue : fieldsDisplayValues) {
			if (fieldsDisplayValue.startsWith(prefix)) {
				String fieldIstanceId = StringUtil.extractLast(
					fieldsDisplayValue, DDMImpl.INSTANCE_SEPARATOR);

				if (fieldIstanceId.equals(instanceId)) {
					return offset;
				}

				offset++;
			}
		}

		return -1;
	}

	protected List<Serializable> getFieldValues(
			DDMStructure ddmStructure, String fieldName, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(fieldName);

		String fieldDataType = ddmFormField.getDataType();
		String fieldType = ddmFormField.getType();

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		List<String> fieldNames = getFieldNames(
			fieldNamespace, fieldName, serviceContext);

		List<Serializable> fieldValues = new ArrayList<>(fieldNames.size());

		for (String fieldNameValue : fieldNames) {
			Serializable fieldValue = serviceContext.getAttribute(
				fieldNameValue);

			if (Validator.isNull(fieldValue)) {
				fieldValue = predefinedValue.getString(
					serviceContext.getLocale());
			}

			if (fieldType.equals(DDMImpl.TYPE_CHECKBOX) &&
				Validator.isNull(fieldValue)) {

				fieldValue = "false";
			}
			else if (fieldDataType.equals(FieldConstants.DATE)) {
				Date fieldValueDate = null;

				if (Validator.isNull(fieldValue)) {
					int fieldValueMonth = GetterUtil.getInteger(
						serviceContext.getAttribute(fieldNameValue + "Month"));
					int fieldValueDay = GetterUtil.getInteger(
						serviceContext.getAttribute(fieldNameValue + "Day"));
					int fieldValueYear = GetterUtil.getInteger(
						serviceContext.getAttribute(fieldNameValue + "Year"));

					fieldValueDate = _portal.getDate(
						fieldValueMonth, fieldValueDay, fieldValueYear,
						TimeZoneUtil.getTimeZone("UTC"), null);
				}
				else {
					try {
						fieldValueDate = DateUtil.parseDate(
							String.valueOf(fieldValue),
							serviceContext.getLocale());
					}
					catch (ParseException pe) {
						_log.error("Unable to parse date " + fieldValue);
					}
				}

				if (fieldValueDate != null) {
					fieldValue = String.valueOf(fieldValueDate.getTime());
				}
			}
			else if (fieldDataType.equals(FieldConstants.IMAGE) &&
					 Validator.isNull(fieldValue)) {

				HttpServletRequest httpServletRequest =
					serviceContext.getRequest();

				if (httpServletRequest instanceof UploadRequest) {
					String imageFieldValue = getImageFieldValue(
						(UploadRequest)httpServletRequest, fieldNameValue);

					if (Validator.isNotNull(imageFieldValue)) {
						fieldValue = imageFieldValue;
					}
				}
			}

			if (Validator.isNull(fieldValue)) {
				return null;
			}

			if (DDMImpl.TYPE_SELECT.equals(fieldType)) {
				String predefinedValueString = predefinedValue.getString(
					serviceContext.getLocale());

				if (!fieldValue.equals(predefinedValueString) &&
					(fieldValue instanceof String)) {

					fieldValue = new String[] {String.valueOf(fieldValue)};

					fieldValue = JSONFactoryUtil.serialize(fieldValue);
				}
			}

			Serializable fieldValueSerializable =
				FieldConstants.getSerializable(
					fieldDataType, GetterUtil.getString(fieldValue));

			fieldValues.add(fieldValueSerializable);
		}

		return fieldValues;
	}

	protected List<Serializable> getFieldValues(Field field, Locale locale) {
		Map<Locale, List<Serializable>> fieldValuesMap = field.getValuesMap();

		return fieldValuesMap.get(locale);
	}

	protected byte[] getImageBytes(
			UploadRequest uploadRequest, String fieldNameValue)
		throws Exception {

		File file = uploadRequest.getFile(fieldNameValue + "File");

		byte[] bytes = FileUtil.getBytes(file);

		if (ArrayUtil.isNotEmpty(bytes)) {
			return bytes;
		}

		String url = uploadRequest.getParameter(fieldNameValue + "URL");

		long imageId = GetterUtil.getLong(
			_http.getParameter(url, "img_id", false));

		Image image = _imageLocalService.fetchImage(imageId);

		if (image == null) {
			return null;
		}

		return image.getTextObj();
	}

	protected String getImageFieldValue(
		UploadRequest uploadRequest, String fieldNameValue) {

		try {
			byte[] bytes = getImageBytes(uploadRequest, fieldNameValue);

			if (ArrayUtil.isNotEmpty(bytes)) {
				JSONObject jsonObject = JSONUtil.put(
					"alt", uploadRequest.getParameter(fieldNameValue + "Alt")
				).put(
					"data", UnicodeFormatter.bytesToHex(bytes)
				);

				return jsonObject.toString();
			}
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	protected Set<Locale> getMergedAvailableLocales(
		Set<Locale> newFieldAvailableLocales,
		Set<Locale> existingFieldAvailableLocales) {

		Set<Locale> mergedAvailableLocales = new HashSet<>();

		mergedAvailableLocales.addAll(newFieldAvailableLocales);
		mergedAvailableLocales.addAll(existingFieldAvailableLocales);

		return mergedAvailableLocales;
	}

	protected List<Serializable> getMergedFieldValues(
		String fieldName, List<Serializable> newFieldValues,
		String[] newFieldsDisplayValues, List<Serializable> existingFieldValues,
		String[] existingFieldsDisplayValues,
		List<Serializable> defaultFieldValues) {

		if (existingFieldValues == null) {
			return newFieldValues;
		}

		List<Serializable> mergedLocaleValues = new ArrayList<>();

		int repetition = countFieldRepetition(
			newFieldsDisplayValues, fieldName);

		for (int i = 0; i < repetition; i++) {
			int existingFieldValueIndex = getExistingFieldValueIndex(
				newFieldsDisplayValues, existingFieldsDisplayValues, fieldName,
				i);

			if (existingFieldValueIndex == -1) {
				mergedLocaleValues.add(i, defaultFieldValues.get(i));
			}
			else {
				if (newFieldValues != null) {
					mergedLocaleValues.add(i, newFieldValues.get(i));
				}
				else {
					Serializable existingValue = existingFieldValues.get(
						existingFieldValueIndex);

					mergedLocaleValues.add(i, existingValue);
				}
			}
		}

		return mergedLocaleValues;
	}

	protected Map<Locale, List<Serializable>> getMergedFieldValuesMap(
		Field newField, String[] newFieldsDisplayValues, Field existingField,
		String[] existingFieldsDisplayValues) {

		Set<Locale> availableLocales = getMergedAvailableLocales(
			newField.getAvailableLocales(),
			existingField.getAvailableLocales());

		for (Locale locale : availableLocales) {
			List<Serializable> newFieldValues = getFieldValues(
				newField, locale);

			List<Serializable> existingFieldValues = getFieldValues(
				existingField, locale);

			List<Serializable> defaultFieldValues = getFieldValues(
				newField, newField.getDefaultLocale());

			List<Serializable> mergedLocaleValues = getMergedFieldValues(
				newField.getName(), newFieldValues, newFieldsDisplayValues,
				existingFieldValues, existingFieldsDisplayValues,
				defaultFieldValues);

			existingField.setValues(locale, mergedLocaleValues);
		}

		return existingField.getValuesMap();
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesToFieldsConverter(
		DDMFormValuesToFieldsConverter ddmFormValuesToFieldsConverter) {

		_ddmFormValuesToFieldsConverter = ddmFormValuesToFieldsConverter;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setFieldsToDDMFormValuesConverter(
		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter) {

		_fieldsToDDMFormValuesConverter = fieldsToDDMFormValuesConverter;
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	protected String[] splitFieldsDisplayValue(Field fieldsDisplayField) {
		String value = (String)fieldsDisplayField.getValue();

		return StringUtil.split(value);
	}

	protected void updateDDMFormFieldDefaultLocale(
		DDMFormField ddmFormField, Locale newDefaultLocale) {

		updateDDMFormFieldOptionsDefaultLocale(
			ddmFormField.getDDMFormFieldOptions(), newDefaultLocale);

		updateLocalizedValueDefaultLocale(
			ddmFormField.getLabel(), newDefaultLocale);
		updateLocalizedValueDefaultLocale(
			ddmFormField.getPredefinedValue(), newDefaultLocale);
		updateLocalizedValueDefaultLocale(
			ddmFormField.getStyle(), newDefaultLocale);
		updateLocalizedValueDefaultLocale(
			ddmFormField.getTip(), newDefaultLocale);
	}

	protected void updateDDMFormFieldOptionsDefaultLocale(
		DDMFormFieldOptions ddmFormFieldOptions, Locale newDefaultLocale) {

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		for (LocalizedValue localizedValue : options.values()) {
			updateLocalizedValueDefaultLocale(localizedValue, newDefaultLocale);
		}

		ddmFormFieldOptions.setDefaultLocale(newDefaultLocale);
	}

	protected void updateDDMFormFieldsDefaultLocale(
		List<DDMFormField> ddmFormFields, Locale newDefaultLocale) {

		for (DDMFormField ddmFormField : ddmFormFields) {
			updateDDMFormFieldDefaultLocale(ddmFormField, newDefaultLocale);

			updateDDMFormFieldsDefaultLocale(
				ddmFormField.getNestedDDMFormFields(), newDefaultLocale);
		}
	}

	protected void updateLocalizedValueDefaultLocale(
		LocalizedValue localizedValue, Locale newDefaultLocale) {

		Set<Locale> availableLocales = localizedValue.getAvailableLocales();

		if (!availableLocales.contains(newDefaultLocale)) {
			String defaultValueString = localizedValue.getString(
				localizedValue.getDefaultLocale());

			localizedValue.addString(newDefaultLocale, defaultValueString);
		}

		localizedValue.setDefaultLocale(newDefaultLocale);
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMImpl.class);

	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private Http _http;

	private ImageLocalService _imageLocalService;

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _jsonDDMFormDeserializer;

	@Reference(target = "(ddm.form.serializer.type=json)")
	private DDMFormSerializer _jsonDDMFormSerializer;

	@Reference(target = "(ddm.form.values.deserializer.type=json)")
	private DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;

	@Reference(target = "(ddm.form.values.serializer.type=json)")
	private DDMFormValuesSerializer _jsonDDMFormValuesSerializer;

	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}