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

package com.liferay.dynamic.data.mapping;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormJSONDeserializerImpl;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateImpl;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.test.util.DDMFormFieldTypeSettingsTestUtil;
import com.liferay.dynamic.data.mapping.util.impl.DDMImpl;
import com.liferay.portal.configuration.ConfigurationFactoryImpl;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.LocalizationImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Pablo Carvalho
 * @author Miguel Angelo Caldas Gallindo
 */
@PrepareForTest(
	{
		DDMStructureLocalServiceUtil.class, DDMTemplateLocalServiceUtil.class,
		LocaleUtil.class, PortalClassLoaderUtil.class, ResourceBundleUtil.class
	}
)
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	{
		"com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil",
		"com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil"
	}
)
public abstract class BaseDDMTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpPortalClassLoaderUtil();
		setUpResourceBundleUtil();
	}

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

		metadataElement.addAttribute(
			"locale", LocaleUtil.toLanguageId(LocaleUtil.US));

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", "label");
		entryElement.setText(label);

		return dynamicElement;
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		Set<Locale> availableLocales = new LinkedHashSet<>();

		for (Locale locale : locales) {
			availableLocales.add(locale);
		}

		return availableLocales;
	}

	protected Field createBRField(
		long ddmStructureId, String fieldName, List<Serializable> ptValues) {

		return new MockField(
			ddmStructureId, fieldName, ptValues, LocaleUtil.BRAZIL);
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

	protected DDMFormLayoutColumn createDDMFormLayoutColumn(
		int size, String... fieldNames) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			size, fieldNames);

		return ddmFormLayoutColumn;
	}

	protected List<DDMFormLayoutColumn> createDDMFormLayoutColumns(
		String... fieldNames) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns = new ArrayList<>();

		int size = 12 / fieldNames.length;

		for (String fieldName : fieldNames) {
			ddmFormLayoutColumns.add(new DDMFormLayoutColumn(size, fieldName));
		}

		return ddmFormLayoutColumns;
	}

	protected DDMFormLayoutRow createDDMFormLayoutRow(
		DDMFormLayoutColumn... ddmFormLayoutColumns) {

		return createDDMFormLayoutRow(Arrays.asList(ddmFormLayoutColumns));
	}

	protected DDMFormLayoutRow createDDMFormLayoutRow(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(ddmFormLayoutColumns);

		return ddmFormLayoutRow;
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

	protected DDMFormField createParagraphDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "paragraph");

		ddmFormField.setLocalizable(false);
		ddmFormField.setRepeatable(false);
		ddmFormField.setRequired(false);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, name);

		return ddmFormField;
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
		return createStructure(name, ddmFormJSONSerializer.serialize(ddmForm));
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
		List<Serializable> values = new ArrayList<>();

		for (String valueString : valuesString) {
			values.add(valueString);
		}

		return values;
	}

	protected Map<Locale, List<Serializable>> createValuesMap(
		List<Serializable> enValues, List<Serializable> ptValues) {

		Map<Locale, List<Serializable>> valuesMap = new HashMap<>();

		if (enValues != null) {
			valuesMap.put(LocaleUtil.US, enValues);
		}

		if (ptValues != null) {
			valuesMap.put(LocaleUtil.BRAZIL, ptValues);
		}

		return valuesMap;
	}

	protected DDMFormFieldTypeServicesTracker
		getMockedDDMFormFieldTypeServicesTracker() {

		setUpDefaultDDMFormFieldType();

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker = mock(
			DDMFormFieldTypeServicesTracker.class);

		DDMFormFieldRenderer ddmFormFieldRenderer = mock(
			DDMFormFieldRenderer.class);

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldRenderer(
				Matchers.anyString())
		).thenReturn(
			ddmFormFieldRenderer
		);

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
				Matchers.anyString())
		).thenReturn(
			_defaultDDMFormFieldType
		);

		Map<String, Object> properties = new HashMap<>();

		properties.put("ddm.form.field.type.icon", "my-icon");
		properties.put(
			"ddm.form.field.type.js.class.name", "myJavaScriptClass");
		properties.put("ddm.form.field.type.js.module", "myJavaScriptModule");

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				Matchers.anyString())
		).thenReturn(
			properties
		);

		return ddmFormFieldTypeServicesTracker;
	}

	protected DDMStructure getStructure(long structureId) {
		try {
			return DDMStructureLocalServiceUtil.getStructure(structureId);
		}
		catch (Exception e) {
			return null;
		}
	}

	protected DDMForm getStructureDDMForm(DDMStructure structure) {
		try {
			return DDMStructureLocalServiceUtil.getStructureDDMForm(structure);
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

	protected void setUpConfigurationFactoryUtil() {
		ConfigurationFactoryUtil.setConfigurationFactory(
			new ConfigurationFactoryImpl());
	}

	protected void setUpDDMFormJSONDeserializer() throws Exception {

		// DDM form field type services tracker

		java.lang.reflect.Field field = ReflectionUtil.getDeclaredField(
			DDMFormJSONDeserializerImpl.class,
			"_ddmFormFieldTypeServicesTracker");

		field.set(
			ddmFormJSONDeserializer,
			getMockedDDMFormFieldTypeServicesTracker());

		// JSON factory

		field = ReflectionUtil.getDeclaredField(
			DDMFormJSONDeserializerImpl.class, "_jsonFactory");

		field.set(ddmFormJSONDeserializer, new JSONFactoryImpl());
	}

	protected void setUpDDMFormJSONSerializer() throws Exception {

		// DDM form field type services tracker

		java.lang.reflect.Field field = ReflectionUtil.getDeclaredField(
			DDMFormJSONSerializerImpl.class,
			"_ddmFormFieldTypeServicesTracker");

		field.set(
			ddmFormJSONSerializer, getMockedDDMFormFieldTypeServicesTracker());

		// JSON factory

		field = ReflectionUtil.getDeclaredField(
			DDMFormJSONSerializerImpl.class, "_jsonFactory");

		field.set(ddmFormJSONSerializer, new JSONFactoryImpl());
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

		when(
			getStructureDDMForm(Matchers.any(DDMStructure.class))
		).then(
			new Answer<DDMForm>() {

				@Override
				public DDMForm answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					DDMStructure structure = (DDMStructure)args[0];

					return ddmFormJSONDeserializer.deserialize(
						structure.getDefinition());
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

	protected void setUpDefaultDDMFormFieldType() {
		when(
			_defaultDDMFormFieldType.getDDMFormFieldTypeSettings()
		).then(
			new Answer<Class<? extends DDMFormFieldTypeSettings>>() {

				@Override
				public Class<? extends DDMFormFieldTypeSettings> answer(
						InvocationOnMock invocationOnMock)
					throws Throwable {

					return DDMFormFieldTypeSettingsTestUtil.getSettings();
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
		Set<Locale> availableLocales = SetUtil.fromArray(
			new Locale[] {LocaleUtil.BRAZIL, LocaleUtil.SPAIN, LocaleUtil.US});

		whenLanguageGetAvailableLocalesThen(availableLocales);

		whenLanguageGet(LocaleUtil.BRAZIL, "no", "Não");
		whenLanguageGet(LocaleUtil.BRAZIL, "yes", "Sim");
		whenLanguageGet(LocaleUtil.SPAIN, "latitude", "Latitud");
		whenLanguageGet(LocaleUtil.SPAIN, "longitude", "Longitud");
		whenLanguageGet(LocaleUtil.US, "latitude", "Latitude");
		whenLanguageGet(LocaleUtil.US, "longitude", "Longitude");
		whenLanguageGet(LocaleUtil.US, "no", "No");
		whenLanguageGet(LocaleUtil.US, "yes", "Yes");

		whenLanguageGetLanguageId(LocaleUtil.BRAZIL, "pt_BR");
		whenLanguageGetLanguageId(LocaleUtil.SPAIN, "es_ES");
		whenLanguageGetLanguageId(LocaleUtil.US, "en_US");

		whenLanguageIsAvailableLocale("en_US");
		whenLanguageIsAvailableLocale("es_ES");
		whenLanguageIsAvailableLocale("pt_BR");

		whenLanguageIsAvailableLocale(LocaleUtil.BRAZIL);
		whenLanguageIsAvailableLocale(LocaleUtil.SPAIN);
		whenLanguageIsAvailableLocale(LocaleUtil.US);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
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
			LocaleUtil.getDefault()
		).thenReturn(
			LocaleUtil.US
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

	protected void setUpPortalClassLoaderUtil() {
		mockStatic(PortalClassLoaderUtil.class);

		when(
			PortalClassLoaderUtil.getClassLoader()
		).thenReturn(
			_classLoader
		);
	}

	protected void setUpPropsUtil() throws Exception {
		Props props = mock(Props.class);

		when(
			props.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN)
		).thenReturn(
			"yyyyMMddHHmmss"
		);

		when(
			props.get(PropsKeys.XML_SECURITY_ENABLED)
		).thenReturn(
			Boolean.TRUE.toString()
		);

		PropsUtil.setProps(props);
	}

	protected void setUpResourceBundleUtil() {
		mockStatic(ResourceBundleUtil.class);

		when(
			ResourceBundleUtil.getBundle(
				"content.Language", LocaleUtil.BRAZIL, _classLoader)
		).thenReturn(
			_resourceBundle
		);

		when(
			ResourceBundleUtil.getBundle(
				"content.Language", LocaleUtil.US, _classLoader)
		).thenReturn(
			_resourceBundle
		);
	}

	protected void setUpSAXReaderUtil() {
		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		SAXReaderImpl secureSAXReader = new SAXReaderImpl();

		secureSAXReader.setSecure(true);

		saxReaderUtil.setSAXReader(secureSAXReader);

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		SAXReaderImpl unsecureSAXReader = new SAXReaderImpl();

		unsecureSAXReaderUtil.setSAXReader(unsecureSAXReader);
	}

	protected void whenLanguageGet(
		Locale locale, String key, String returnValue) {

		when(
			language.get(Matchers.eq(locale), Matchers.eq(key))
		).thenReturn(
			returnValue
		);
	}

	protected void whenLanguageGetAvailableLocalesThen(
		Set<Locale> availableLocales) {

		when(
			language.getAvailableLocales()
		).thenReturn(
			availableLocales
		);
	}

	protected void whenLanguageGetLanguageId(Locale locale, String languageId) {
		when(
			language.getLanguageId(Matchers.eq(locale))
		).thenReturn(
			languageId
		);
	}

	protected void whenLanguageIsAvailableLocale(Locale locale) {
		when(
			language.isAvailableLocale(Matchers.eq(locale))
		).thenReturn(
			true
		);
	}

	protected void whenLanguageIsAvailableLocale(String languageId) {
		when(
			language.isAvailableLocale(Matchers.eq(languageId))
		).thenReturn(
			true
		);
	}

	protected final DDMFormJSONDeserializer ddmFormJSONDeserializer =
		new DDMFormJSONDeserializerImpl();
	protected final DDMFormJSONSerializer ddmFormJSONSerializer =
		new DDMFormJSONSerializerImpl();

	@Mock
	protected Language language;

	protected Map<Long, DDMStructure> structures = new HashMap<>();
	protected Map<Long, DDMTemplate> templates = new HashMap<>();

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

	@Mock
	private ClassLoader _classLoader;

	@Mock
	private DDMFormFieldType _defaultDDMFormFieldType;

	@Mock
	private ResourceBundle _resourceBundle;

}