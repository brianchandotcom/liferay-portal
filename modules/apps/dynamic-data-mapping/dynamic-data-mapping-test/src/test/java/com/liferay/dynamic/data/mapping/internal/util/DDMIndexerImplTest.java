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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.internal.test.util.DDMFixture;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Lino Alves
 * @author André de Oliveira
 */
@PrepareOnlyThisForTest(
	{DDMStructureLocalServiceUtil.class, ResourceBundleUtil.class}
)
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	{
		"com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl",
		"com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil"
	}
)
public class DDMIndexerImplTest {

	@Before
	public void setUp() throws Exception {
		ddmFixture.setUp();
		documentFixture.setUp();
		setUpPortalUtil();
	}

	@After
	public void tearDown() throws Exception {
		ddmFixture.tearDown();

		documentFixture.tearDown();
	}

	@Test
	public void testFormWithOneAvailableLocaleSameAsDefaultLocale() {
		Locale defaultLocale = LocaleUtil.JAPAN;
		Locale translationLocale = LocaleUtil.JAPAN;

		Set<Locale> availableLocales = Collections.singleton(defaultLocale);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			availableLocales, defaultLocale);

		String fieldName = "text1";
		String indexType = "text";

		ddmForm.addDDMFormField(createDDMFormField(fieldName, indexType));

		String fieldValue = "新規作成";

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			fieldName, translationLocale, fieldValue, defaultLocale);

		Document document = createDocument();

		DDMStructure ddmStructure = createDDMStructure(ddmForm);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmForm, ddmFormFieldValue);

		ddmIndexer.addAttributes(document, ddmStructure, ddmFormValues);

		Map<String, String> map = _withSortableValues(
			Collections.singletonMap(
				"ddm__text__NNNNN__text1_ja_JP", fieldValue));

		FieldValuesAssert.assertFieldValues(
			_replaceKeys(
				"NNNNN", String.valueOf(ddmStructure.getStructureId()), map),
			"ddm__text", document, fieldValue);
	}

	@Test
	public void testFormWithTwoAvailableLocalesAndFieldWithNondefaultLocale() {
		Locale defaultLocale = LocaleUtil.US;
		Locale translationLocale = LocaleUtil.JAPAN;

		Set<Locale> availableLocales = new HashSet<>(
			Arrays.asList(defaultLocale, translationLocale));

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			availableLocales, defaultLocale);

		String fieldName = "text1";
		String indexType = "text";

		ddmForm.addDDMFormField(createDDMFormField(fieldName, indexType));

		String fieldValue = "新規作成";

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			fieldName, translationLocale, fieldValue, defaultLocale);

		Document document = createDocument();

		DDMStructure ddmStructure = createDDMStructure(ddmForm);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmForm, ddmFormFieldValue);

		ddmIndexer.addAttributes(document, ddmStructure, ddmFormValues);

		Map<String, String> map = _withSortableValues(
			Collections.singletonMap(
				"ddm__text__NNNNN__text1_ja_JP", fieldValue));

		FieldValuesAssert.assertFieldValues(
			_replaceKeys(
				"NNNNN", String.valueOf(ddmStructure.getStructureId()), map),
			"ddm__text", document, fieldValue);
	}

	@Test
	public void testFormWithTwoAvailableLocalesAndFieldWithTwoLocales() {
		Locale defaultLocale = LocaleUtil.JAPAN;
		Locale translationLocale = LocaleUtil.US;

		Set<Locale> availableLocales = new HashSet<>(
			Arrays.asList(defaultLocale, translationLocale));

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			availableLocales, defaultLocale);

		String fieldName = "text1";
		String indexType = "text";

		DDMFormField ddmFormField = createDDMFormField(fieldName, indexType);

		ddmForm.addDDMFormField(ddmFormField);

		String fieldValueJP = "新規作成";
		String fieldValueUS = "Create New";

		DDMFormFieldValue ddmFormFieldValueJP = createDDMFormFieldValue(
			fieldName, defaultLocale, fieldValueJP, defaultLocale);

		DDMFormFieldValue ddmFormFieldValueUS = createDDMFormFieldValue(
			fieldName, translationLocale, fieldValueUS, defaultLocale);

		Document document = createDocument();

		DDMStructure ddmStructure = createDDMStructure(ddmForm);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmForm, ddmFormFieldValueJP, ddmFormFieldValueUS);

		ddmIndexer.addAttributes(document, ddmStructure, ddmFormValues);

		Map<String, String> map = _withSortableValues(
			new HashMap<String, String>() {
				{
					put("ddm__text__NNNNN__text1_en_US", fieldValueUS);
					put("ddm__text__NNNNN__text1_ja_JP", fieldValueJP);
				}
			});

		FieldValuesAssert.assertFieldValues(
			_replaceKeys(
				"NNNNN", String.valueOf(ddmStructure.getStructureId()), map),
			"ddm__text", document, fieldValueJP);
	}

	protected DDMFormField createDDMFormField(
		String fieldName, String indexType) {

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			fieldName, true, false, true);

		ddmFormField.setIndexType(indexType);

		return ddmFormField;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String name, Locale locale, String valueString, Locale defaultLocale) {

		LocalizedValue localizedValue = new LocalizedValue(defaultLocale);

		localizedValue.addString(locale, valueString);

		return DDMFormValuesTestUtil.createDDMFormFieldValue(
			name, localizedValue);
	}

	protected DDMFormJSONSerializer createDDMFormJSONSerializer() {
		return new DDMFormJSONSerializer() {
			{
				setDDMFormFieldTypeServicesTracker(
					Mockito.mock(DDMFormFieldTypeServicesTracker.class));

				setJSONFactory(new JSONFactoryImpl());
			}
		};
	}

	protected DDMFormValues createDDMFormValues(
		DDMForm ddmForm, DDMFormFieldValue... ddmFormFieldValues) {

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	protected DDMIndexer createDDMIndexer() {
		return new DDMIndexerImpl() {
			{
				setDDMFormValuesToFieldsConverter(
					new DDMFormValuesToFieldsConverterImpl());
			}
		};
	}

	protected DDMStructure createDDMStructure(DDMForm ddmForm) {
		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setDefinition(serialize(ddmForm));

		ddmStructure.setDDMForm(ddmForm);

		ddmStructure.setStructureId(RandomTestUtil.randomLong());
		ddmStructure.setName(RandomTestUtil.randomString());

		ddmFixture.whenDDMStructureLocalServiceFetchStructure(ddmStructure);

		return ddmStructure;
	}

	protected Document createDocument() {
		return DocumentFixture.newDocument(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			DDMForm.class.getName());
	}

	protected String serialize(DDMForm ddmForm) {
		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			ddmFormJSONSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = PowerMockito.mock(Portal.class);

		ResourceBundle resourceBundle = PowerMockito.mock(ResourceBundle.class);

		PowerMockito.when(
			portal.getResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
	}

	protected final DDMFixture ddmFixture = new DDMFixture();
	protected final DDMFormJSONSerializer ddmFormJSONSerializer =
		createDDMFormJSONSerializer();
	protected final DDMIndexer ddmIndexer = createDDMIndexer();
	protected final DocumentFixture documentFixture = new DocumentFixture();

	private static Map<String, String> _replaceKeys(
		String oldSub, String newSub, Map<String, String> map) {

		Set<Map.Entry<String, String>> entrySet = map.entrySet();

		Stream<Map.Entry<String, String>> entries = entrySet.stream();

		return entries.collect(
			Collectors.toMap(
				entry -> StringUtil.replace(entry.getKey(), oldSub, newSub),
				Map.Entry::getValue));
	}

	private static Map<String, String> _withSortableValues(
		Map<String, String> map) {

		Set<Map.Entry<String, String>> entrySet = map.entrySet();

		Stream<Map.Entry<String, String>> entries = entrySet.stream();

		Map<String, String> map2 = entries.collect(
			Collectors.toMap(
				entry -> entry.getKey() + "_String_sortable",
				entry -> StringUtil.toLowerCase(entry.getValue())));

		map2.putAll(map);

		return map2;
	}

}