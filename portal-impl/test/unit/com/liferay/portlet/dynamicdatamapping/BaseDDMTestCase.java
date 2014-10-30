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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.LocalizationImpl;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.xml.SAXReaderImpl;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerImpl;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDSerializerImpl;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateImpl;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Pablo Carvalho
 * @author Miguel Angelo Caldas Gallindo
 */
@RunWith(PowerMockRunner.class)
public abstract class BaseDDMTestCase extends PowerMockito {

	protected void addDDMFormFields(
		DDMForm ddmForm, DDMFormField... ddmFormFieldsArray) {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFieldsArray) {
			ddmFormFields.add(ddmFormField);
		}
	}

	protected void addNestedTextDDMFormFields(
		DDMFormField ddmFormField, String... fieldNames) {

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		for (String fieldName : fieldNames) {
			nestedDDMFormFields.add(createTextDDMFormField(fieldName));
		}
	}

	protected void addTextDDMFormFields(DDMForm ddmForm, String... fieldNames) {
		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (String fieldName : fieldNames) {
			ddmFormFields.add(createTextDDMFormField(fieldName));
		}
	}

	protected Element addTextElement(
		Element element, String name, String label, boolean localizable) {

		Element dynamicElement = element.addElement("dynamic-element");

		dynamicElement.addAttribute("dataType", "string");
		dynamicElement.addAttribute("localizable", String.valueOf(localizable));
		dynamicElement.addAttribute("name", name);
		dynamicElement.addAttribute("type", "text");

		Element metadataElement = dynamicElement.addElement("meta-data");

		metadataElement.addAttribute("locale", LocaleUtil.toLanguageId(
			LocaleUtil.US));

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", "label");
		entryElement.setText(label);

		return dynamicElement;
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		Set<Locale> availableLocales = new LinkedHashSet<Locale>();

		for (Locale locale : locales) {
			availableLocales.add(locale);
		}

		return availableLocales;
	}

	protected DDMForm createDDMForm(
		Set<Locale> availableLocales, Locale defaultLocale) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(availableLocales);
		ddmForm.setDefaultLocale(defaultLocale);

		return ddmForm;
	}

	protected DDMForm createDDMForm(String... fieldNames) {
		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		addTextDDMFormFields(ddmForm, fieldNames);

		return ddmForm;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String instanceId, String name, Value value) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String name, Value value) {

		return createDDMFormFieldValue(StringUtil.randomString(), name, value);
	}

	protected DDMFormValues createDDMFormValues(DDMForm ddmForm) {
		return createDDMFormValues(
			ddmForm, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);
	}

	protected DDMFormValues createDDMFormValues(
		DDMForm ddmForm, Set<Locale> availableLocales, Locale defaultLocale) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(availableLocales);
		ddmFormValues.setDefaultLocale(defaultLocale);

		return ddmFormValues;
	}

	protected Document createDocument(String... fieldNames) {
		Document document = createEmptyDocument();

		for (String fieldName : fieldNames) {
			addTextElement(
				document.getRootElement(), fieldName, fieldName, false);
		}

		return document;
	}

	protected Document createEmptyDocument() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", "en_US");
		rootElement.addAttribute("default-locale", "en_US");

		return document;
	}

	protected Field createField(
		long ddmStructureId, String fieldName, List<Serializable> enValues,
		List<Serializable> ptValues) {

		Map<Locale, List<Serializable>> valuesMap = createValuesMap(
			enValues, ptValues);

		return new MockField(
			ddmStructureId, fieldName, valuesMap, LocaleUtil.US);
	}

	protected Fields createFields(Field... fieldsArray) {
		Fields fields = new Fields();

		for (Field field : fieldsArray) {
			fields.put(field);
		}

		return fields;
	}

	protected Field createFieldsDisplayField(
		long ddmStructureId, String value) {

		Field fieldsDisplayField = new MockField(
			ddmStructureId, DDMImpl.FIELDS_DISPLAY_NAME,
			createValuesList(value), LocaleUtil.US);

		fieldsDisplayField.setDefaultLocale(LocaleUtil.US);

		return fieldsDisplayField;
	}

	protected Value createLocalizedValue(
		String enValue, String ptValue, Locale defaultLocale) {

		Value value = new LocalizedValue(defaultLocale);

		value.addString(LocaleUtil.BRAZIL, ptValue);
		value.addString(LocaleUtil.US, enValue);

		return value;
	}

	protected Document createSampleDocument() {
		Document document = createEmptyDocument();

		addTextElement(
			document.getRootElement(), "Unlocalizable", "Text 2", false);

		return document;
	}

	protected DDMFormField createSeparatorDDMFormField(
		String name, boolean repeatable) {

		DDMFormField ddmFormField = new DDMFormField(name, "separator");

		ddmFormField.setRepeatable(repeatable);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, name);

		return ddmFormField;
	}

	protected DDMStructure createStructure(String name, DDMForm ddmForm) {
		return createStructure(
			name, DDMFormXSDSerializerUtil.serialize(ddmForm));
	}

	protected DDMStructure createStructure(String name, String definition) {
		DDMStructure structure = new DDMStructureImpl();

		structure.setStructureId(RandomTestUtil.randomLong());
		structure.setName(name);
		structure.setDefinition(definition);

		structures.put(structure.getStructureId(), structure);

		return structure;
	}

	protected DDMStructure createStructure(String name, String... fieldNames) {
		DDMForm ddmForm = createDDMForm(fieldNames);

		return createStructure(name, ddmForm);
	}

	protected DDMTemplate createTemplate(
		long templateId, String name, String mode, String script) {

		DDMTemplate template = new DDMTemplateImpl();

		template.setTemplateId(templateId);
		template.setName(name);
		template.setMode(mode);
		template.setScript(script);

		templates.put(template.getTemplateId(), template);

		return template;
	}

	protected DDMFormField createTextDDMFormField(String name) {
		return createTextDDMFormField(name, name, true, false, false);
	}

	protected DDMFormField createTextDDMFormField(
		String name, String label, boolean localizable, boolean repeatable,
		boolean required) {

		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");
		ddmFormField.setLocalizable(localizable);
		ddmFormField.setRepeatable(repeatable);
		ddmFormField.setRequired(required);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, label);

		return ddmFormField;
	}

	protected List<Serializable> createValuesList(String... valuesString) {
		List<Serializable> values = new ArrayList<Serializable>();

		for (String valueString : valuesString) {
			values.add(valueString);
		}

		return values;
	}

	protected Map<Locale, List<Serializable>> createValuesMap(
		List<Serializable> enValues, List<Serializable> ptValues) {

		Map<Locale, List<Serializable>> valuesMap =
			new HashMap<Locale, List<Serializable>>();

		if (enValues != null) {
			valuesMap.put(LocaleUtil.US, enValues);
		}

		if (ptValues != null) {
			valuesMap.put(LocaleUtil.BRAZIL, ptValues);
		}

		return valuesMap;
	}

	protected DDMStructure getStructure(long structureId) {
		try {
			return DDMStructureLocalServiceUtil.getStructure(structureId);
		}
		catch (Exception e) {
			return null;
		}
	}

	protected DDMTemplate getTemplate(long templateId) {
		try {
			return DDMTemplateLocalServiceUtil.getTemplate(templateId);
		}
		catch (Exception e) {
			return null;
		}
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void setUpDDMFormXSDDeserializerUtil() {
		DDMFormXSDDeserializerUtil ddmFormXSDDeserializerUtil =
			new DDMFormXSDDeserializerUtil();

		ddmFormXSDDeserializerUtil.setDDMFormXSDDeserializer(
			new DDMFormXSDDeserializerImpl());
	}

	protected void setUpDDMFormXSDSerializerUtil() {
		DDMFormXSDSerializerUtil ddmFormXSDSerializerUtil =
			new DDMFormXSDSerializerUtil();

		ddmFormXSDSerializerUtil.setDDMFormXSDSerializer(
			new DDMFormXSDSerializerImpl());
	}

	protected void setUpDDMStructureLocalServiceUtil() {
		mockStatic(DDMStructureLocalServiceUtil.class);

		when(
			getStructure(Matchers.anyLong())
		).then(
			new Answer<DDMStructure>() {

				@Override
				public DDMStructure answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					Long structureId = (Long)args[0];

					return structures.get(structureId);
				}

			}
		);
	}

	protected void setUpDDMTemplateLocalServiceUtil() {
		mockStatic(DDMTemplateLocalServiceUtil.class);

		when(
			getTemplate(Matchers.anyLong())
		).then(
			new Answer<DDMTemplate>() {

				@Override
				public DDMTemplate answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					Long templateId = (Long)args[0];

					return templates.get(templateId);
				}

			}
		);
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected void setUpLanguageUtil() {
		whenLanguageGet(LocaleUtil.BRAZIL, "no", "Não");
		whenLanguageGet(LocaleUtil.BRAZIL, "yes", "Sim");
		whenLanguageGet(LocaleUtil.SPAIN, "latitude", "Latitud");
		whenLanguageGet(LocaleUtil.SPAIN, "longitude", "Longitud");
		whenLanguageGet(LocaleUtil.US, "latitude", "Latitude");
		whenLanguageGet(LocaleUtil.US, "longitude", "Longitude");
		whenLanguageGet(LocaleUtil.US, "no", "No");
		whenLanguageGet(LocaleUtil.US, "yes", "Yes");

		whenLanguageGetLanguageId(LocaleUtil.US, "en_US");
		whenLanguageGetLanguageId(LocaleUtil.BRAZIL, "pt_BR");

		whenLanguageIsAvailableLocale("en_US", true);
		whenLanguageIsAvailableLocale("pt_BR", true);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	protected void setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.fromLanguageId("pt_BR")
		).thenReturn(
			LocaleUtil.BRAZIL
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.BRAZIL)
		).thenReturn(
			"pt_BR"
		);

		when(
			LocaleUtil.toLanguageIds((Locale[])Matchers.any())
		).then(
			new Answer<String[]>() {

				@Override
				public String[] answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					Locale[] locales = (Locale[])args[0];

					String[] languageIds = new String[locales.length];

					for (int i = 0; i < locales.length; i++) {
						languageIds[i] = LocaleUtil.toLanguageId(locales[i]);
					}

					return languageIds;
				}
			}
		);
	}

	protected void setUpLocalizationUtil() {
		spy(LocalizationUtil.class);

		when(
			LocalizationUtil.getLocalization()
		).thenReturn(
			new LocalizationImpl()
		);
	}

	protected void setUpPropsUtil() throws Exception {
		Props props = mock(Props.class);

		when(
			props.get(PropsKeys.DYNAMIC_DATA_MAPPING_IMAGE_EXTENSIONS)
		).thenReturn(
			".gif,.jpeg,.jpg,.png"
		);

		when(
			props.get(PropsKeys.DYNAMIC_DATA_MAPPING_IMAGE_SMALL_MAX_SIZE)
		).thenReturn(
			"51200"
		);

		String ddmStructurePrivateFieldDataTypeKey =
			PropsKeys.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_DATATYPE;

		when(
			props.get(
				Matchers.eq(ddmStructurePrivateFieldDataTypeKey),
				Matchers.any(Filter.class))
		).thenReturn(
			"string"
		);

		String ddmStructurePrivateFieldRepeatableKey =
			PropsKeys.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_REPEATABLE;

		when(
			props.get(
				Matchers.eq(ddmStructurePrivateFieldRepeatableKey),
				Matchers.any(Filter.class))
		).thenReturn(
			"false"
		);

		when(
			props.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN)
		).thenReturn(
			"yyyyMMddHHmmss"
		);

		PropsUtil.setProps(props);
	}

	protected void setUpSAXReaderUtil() {
		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		saxReaderUtil.setSAXReader(new SAXReaderImpl());
	}

	protected void whenLanguageGet(
		Locale locale, String key, String returnValue) {

		when(
			_language.get(Matchers.eq(locale), Matchers.eq(key))
		).thenReturn(
			returnValue
		);
	}

	protected void whenLanguageGetLanguageId(Locale locale, String languageId) {
		when(
			_language.getLanguageId(Matchers.eq(locale))
		).thenReturn(
				languageId
		);
	}

	protected void whenLanguageIsAvailableLocale(
		String languageId, boolean returnValue) {

		when(
			_language.isAvailableLocale(Matchers.eq(languageId))
		).thenReturn(
			returnValue
		);
	}

	@Mock
	protected Language _language;

	protected Map<Long, DDMStructure> structures =
		new HashMap<Long, DDMStructure>();
	protected Map<Long, DDMTemplate> templates =
		new HashMap<Long, DDMTemplate>();

	protected class MockField extends Field {

		public MockField(
			long ddmStructureId, String name, List<Serializable> values,
			Locale locale) {

			super(ddmStructureId, name, values, locale);
		}

		public MockField(
			long ddmStructureId, String name,
			Map<Locale, List<Serializable>> valuesMap, Locale defaultLocale) {

			super(ddmStructureId, name, valuesMap, defaultLocale);
		}

		@Override
		public DDMStructure getDDMStructure() {
			return structures.get(getDDMStructureId());
		}

		private static final long serialVersionUID = 1L;

	}

}