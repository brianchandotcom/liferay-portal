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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0;

import com.liferay.dynamic.data.mapping.internal.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;
import com.liferay.portal.util.LocalizationImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.xml.SAXReaderImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
@PowerMockIgnore("javax.xml.stream.*")
@PrepareForTest({LocaleUtil.class, PropsValues.class})
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	{
		"com.liferay.portal.kernel.xml.SAXReaderUtil",
		"com.liferay.portal.util.PropsValues"
	}
)
public class UpgradeDynamicDataMappingTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormValuesJSONDeserializer();
		setUpDDMFormValuesJSONSerializer();
		setUpLanguageUtil();
		setUpLocaleUtil();
		setUpLocalizationUtil();
		setUpPropsValues();
		setUpSecureXMLFactoryProviderUtil();
		setUpSAXReaderUtil();
		setUpJSONFactoryUtil();

		_upgradeDynamicDataMapping = new UpgradeDynamicDataMapping(
			null, null, null, null, null, null, _ddmFormValuesDeserializer,
			_ddmFormValuesSerializer, null, null, null, null, null, null,
			(ResourceActions)ProxyUtil.newProxyInstance(
				UpgradeDynamicDataMappingTest.class.getClassLoader(),
				new Class<?>[] {ResourceActions.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
						Object proxy, Method method, Object[] args) {

						String methodName = method.getName();

						if (methodName.equals("getCompositeModelName")) {
							if (ArrayUtil.isEmpty(args)) {
								return StringPool.BLANK;
							}

							Arrays.sort(args);

							StringBundler sb = new StringBundler(
								args.length * 2);

							for (Object className : args) {
								sb.append(className);
							}

							sb.setIndex(sb.index() - 1);

							return sb.toString();
						}

						return null;
					}

				}),
			null, null, null);
	}

	@Test
	public void testCreateNewFieldNameWithConflictingNewFieldName()
		throws Exception {

		Set<String> existingFieldNames = new HashSet<>();

		existingFieldNames.add("myna");
		existingFieldNames.add("myna1");

		Assert.assertEquals(
			"myna2",
			_upgradeDynamicDataMapping.createNewDDMFormFieldName(
				"?my/--na", existingFieldNames));
	}

	@Test
	public void testCreateNewFieldNameWithSupportedOldFieldName()
		throws Exception {

		Set<String> existingFieldNames = Collections.<String>emptySet();

		Assert.assertEquals(
			"name",
			_upgradeDynamicDataMapping.createNewDDMFormFieldName(
				"name/?--", existingFieldNames));
		Assert.assertEquals(
			"firstName",
			_upgradeDynamicDataMapping.createNewDDMFormFieldName(
				"first Name", existingFieldNames));
		Assert.assertEquals(
			"this_is_a_field_name",
			_upgradeDynamicDataMapping.createNewDDMFormFieldName(
				"this?*&_is///_{{a[[  [_]  ~'field'////>_<name",
				existingFieldNames));
	}

	@Test(expected = UpgradeException.class)
	public void testCreateNewFieldNameWithUnsupportedOldFieldName()
		throws Exception {

		Set<String> existingFieldNames = Collections.<String>emptySet();

		_upgradeDynamicDataMapping.createNewDDMFormFieldName(
			"??????", existingFieldNames);
	}

	@Test
	public void testIsInvalidFieldName() {
		Assert.assertTrue(
			_upgradeDynamicDataMapping.isInvalidFieldName("/name?"));
		Assert.assertTrue(
			_upgradeDynamicDataMapping.isInvalidFieldName("_name--"));
		Assert.assertTrue(
			_upgradeDynamicDataMapping.isInvalidFieldName("name^*"));
		Assert.assertTrue(
			_upgradeDynamicDataMapping.isInvalidFieldName("name^*"));
		Assert.assertTrue(
			_upgradeDynamicDataMapping.isInvalidFieldName("my name"));
	}

	@Test
	public void testIsValidFieldName() {
		Assert.assertFalse(
			_upgradeDynamicDataMapping.isInvalidFieldName("name"));
		Assert.assertFalse(
			_upgradeDynamicDataMapping.isInvalidFieldName("name_"));
		Assert.assertFalse(
			_upgradeDynamicDataMapping.isInvalidFieldName("转注字"));
	}

	@Test
	public void testRenameInvalidDDMFormFieldNamesInJSON() throws Exception {
		long structureId = RandomTestUtil.randomLong();

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", false, false, false);

		ddmFormField.setProperty("oldName", "name<!**>");

		ddmForm.addDDMFormField(ddmFormField);

		_upgradeDynamicDataMapping.populateStructureInvalidDDMFormFieldNamesMap(
			structureId, ddmForm);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"name<!**>", "Joe Bloggs"));

		String serializedDDMFormValues = serialize(ddmFormValues);

		String updatedSerializedDDMFormValues =
			_upgradeDynamicDataMapping.renameInvalidDDMFormFieldNames(
				structureId, serializedDDMFormValues);

		DDMFormValues updatedDDMFormValues = deserialize(
			updatedSerializedDDMFormValues, ddmForm);

		List<DDMFormFieldValue> updatedDDMFormFieldValues =
			updatedDDMFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			updatedDDMFormFieldValues.toString(), 1,
			updatedDDMFormFieldValues.size());

		DDMFormFieldValue updatedDDMFormFieldValue =
			updatedDDMFormFieldValues.get(0);

		Value value = updatedDDMFormFieldValue.getValue();

		Assert.assertEquals("name", updatedDDMFormFieldValue.getName());

		Assert.assertEquals("Joe Bloggs", value.getString(LocaleUtil.US));
	}

	@Test
	public void testRenameInvalidDDMFormFieldNamesInVMTemplate() {
		long structureId = RandomTestUtil.randomLong();

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			"name", false, false, false);

		ddmFormField.setProperty("oldName", "name*");

		ddmForm.addDDMFormField(ddmFormField);

		_upgradeDynamicDataMapping.populateStructureInvalidDDMFormFieldNamesMap(
			structureId, ddmForm);

		String updatedScript =
			_upgradeDynamicDataMapping.renameInvalidDDMFormFieldNames(
				structureId, "Hello $name*!");

		Assert.assertEquals("Hello $name!", updatedScript);
	}

	@Test
	public void testToJSONWithLocalizedAndNestedData() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(createAvailableLocales(LocaleUtil.US));
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormField textDDMFormField = new DDMFormField("Text", "text");

		textDDMFormField.setDataType("string");
		textDDMFormField.setLocalizable(true);
		textDDMFormField.setRepeatable(true);

		DDMFormField textAreaDDMFormField = new DDMFormField(
			"TextArea", "textarea");

		textAreaDDMFormField.setDataType("string");
		textAreaDDMFormField.setLocalizable(true);
		textAreaDDMFormField.setRepeatable(true);

		textDDMFormField.addNestedDDMFormField(textAreaDDMFormField);

		ddmForm.addDDMFormField(textDDMFormField);

		// DDM form values

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(
			createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US));
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		DDMFormFieldValue text1DDMFormFieldValue = createDDMFormFieldValue(
			"srfa", "Text",
			createLocalizedValue(
				"En Text Value 1", "Pt Text Value 1", LocaleUtil.US));

		text1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"elcy", "TextArea",
				createLocalizedValue(
					"En Text Area Value 1", "Pt Text Area Value 1",
					LocaleUtil.US)));
		text1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"uxyj", "TextArea",
				createLocalizedValue(
					"En Text Area Value 2", "Pt Text Area Value 2",
					LocaleUtil.US)));

		ddmFormValues.addDDMFormFieldValue(text1DDMFormFieldValue);

		DDMFormFieldValue text2DDMFormFieldValue = createDDMFormFieldValue(
			"ealq", "Text",
			createLocalizedValue(
				"En Text Value 2", "Pt Text Value 2", LocaleUtil.US));

		text2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"eepy", "TextArea",
				createLocalizedValue(
					"En Text Area Value 3", "Pt Text Area Value 3",
					LocaleUtil.US)));

		ddmFormValues.addDDMFormFieldValue(text2DDMFormFieldValue);

		// XML

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("default-locale", "en_US");
		rootElement.addAttribute("available-locales", "en_US,pt_BR");

		addDynamicElementElement(
			rootElement, "Text",
			new String[] {"En Text Value 1", "En Text Value 2"},
			new String[] {"Pt Text Value 1", "Pt Text Value 2"});
		addDynamicElementElement(
			rootElement, "TextArea",
			new String[] {
				"En Text Area Value 1", "En Text Area Value 2",
				"En Text Area Value 3"
			},
			new String[] {
				"Pt Text Area Value 1", "Pt Text Area Value 2",
				"Pt Text Area Value 3"
			});
		addDynamicElementElement(
			rootElement, "_fieldsDisplay",
			new String[] {
				"Text_INSTANCE_srfa,TextArea_INSTANCE_elcy," +
					"TextArea_INSTANCE_uxyj,Text_INSTANCE_ealq," +
						"TextArea_INSTANCE_eepy"
			});

		String expectedJSON = serialize(ddmFormValues);

		DDMFormValues actualDDMFormValues =
			_upgradeDynamicDataMapping.getDDMFormValues(
				1L, ddmForm, document.asXML());

		String actualJSON = _upgradeDynamicDataMapping.toJSON(
			actualDDMFormValues);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	@Test
	public void testToJSONWithLocalizedData() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(createAvailableLocales(LocaleUtil.US));
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormField textDDMFormField = new DDMFormField("Text", "text");

		textDDMFormField.setDataType("string");
		textDDMFormField.setLocalizable(true);
		textDDMFormField.setRepeatable(true);

		ddmForm.addDDMFormField(textDDMFormField);

		DDMFormField textAreaDDMFormField = new DDMFormField(
			"TextArea", "textarea");

		textAreaDDMFormField.setDataType("string");
		textAreaDDMFormField.setLocalizable(true);
		textAreaDDMFormField.setRepeatable(true);

		ddmForm.addDDMFormField(textAreaDDMFormField);

		DDMFormField integerDDMFormField = new DDMFormField(
			"Integer", "ddm-integer");

		integerDDMFormField.setDataType("integer");
		integerDDMFormField.setLocalizable(false);
		integerDDMFormField.setRepeatable(false);

		ddmForm.addDDMFormField(integerDDMFormField);

		// DDM form values

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(
			createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US));
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"srfa", "Text",
				createLocalizedValue(
					"En Text Value 1", "Pt Text Value 1", LocaleUtil.US)));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"ealq", "Text",
				createLocalizedValue(
					"En Text Value 2", "Pt Text Value 2", LocaleUtil.US)));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"elcy", "TextArea",
				createLocalizedValue(
					"En Text Area Value 1", "Pt Text Area Value 1",
					LocaleUtil.US)));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"uxyj", "TextArea",
				createLocalizedValue(
					"En Text Area Value 2", "Pt Text Area Value 2",
					LocaleUtil.US)));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"eepy", "TextArea",
				createLocalizedValue(
					"En Text Area Value 3", "Pt Text Area Value 3",
					LocaleUtil.US)));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"ckkp", "Integer", new UnlocalizedValue("1")));

		// XML

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("default-locale", "en_US");
		rootElement.addAttribute("available-locales", "en_US,pt_BR");

		addDynamicElementElement(
			rootElement, "Text",
			new String[] {"En Text Value 1", "En Text Value 2"},
			new String[] {"Pt Text Value 1", "Pt Text Value 2"});
		addDynamicElementElement(
			rootElement, "TextArea",
			new String[] {
				"En Text Area Value 1", "En Text Area Value 2",
				"En Text Area Value 3"
			},
			new String[] {
				"Pt Text Area Value 1", "Pt Text Area Value 2",
				"Pt Text Area Value 3"
			});
		addDynamicElementElement(rootElement, "Integer", new String[] {"1"});
		addDynamicElementElement(
			rootElement, "_fieldsDisplay",
			new String[] {
				"Text_INSTANCE_srfa,Text_INSTANCE_ealq," +
					"TextArea_INSTANCE_elcy,TextArea_INSTANCE_uxyj," +
						"TextArea_INSTANCE_eepy,Integer_INSTANCE_ckkp"
			});

		String expectedJSON = serialize(ddmFormValues);

		DDMFormValues actualDDMFormValues =
			_upgradeDynamicDataMapping.getDDMFormValues(
				1L, ddmForm, document.asXML());

		String actualJSON = _upgradeDynamicDataMapping.toJSON(
			actualDDMFormValues);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	@Test
	public void testToJSONWithoutLocalizedData() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(createAvailableLocales(LocaleUtil.US));
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormField textDDMFormField = new DDMFormField("Text", "text");

		textDDMFormField.setDataType("string");
		textDDMFormField.setLocalizable(false);
		textDDMFormField.setRepeatable(false);

		ddmForm.addDDMFormField(textDDMFormField);

		DDMFormField textAreaDDMFormField = new DDMFormField(
			"TextArea", "textarea");

		textAreaDDMFormField.setDataType("string");
		textAreaDDMFormField.setLocalizable(false);
		textAreaDDMFormField.setRepeatable(true);

		ddmForm.addDDMFormField(textAreaDDMFormField);

		// DDM form values

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(
			createAvailableLocales(LocaleUtil.US));
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"hcxo", "Text", new UnlocalizedValue("Text Value")));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"vfqd", "TextArea", new UnlocalizedValue("Text Area Value 1")));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"ycey", "TextArea", new UnlocalizedValue("Text Area Value 2")));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(
				"habt", "TextArea", new UnlocalizedValue("Text Area Value 3")));

		// XML

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("default-locale", "en_US");
		rootElement.addAttribute("available-locales", "en_US");

		addDynamicElementElement(
			rootElement, "Text", new String[] {"Text Value"});
		addDynamicElementElement(
			rootElement, "TextArea",
			new String[] {
				"Text Area Value 1", "Text Area Value 2", "Text Area Value 3"
			});
		addDynamicElementElement(
			rootElement, "_fieldsDisplay",
			new String[] {
				"Text_INSTANCE_hcxo,TextArea_INSTANCE_vfqd," +
					"TextArea_INSTANCE_ycey,TextArea_INSTANCE_habt"
			});

		String expectedJSON = serialize(ddmFormValues);

		DDMFormValues actualDDMFormValues =
			_upgradeDynamicDataMapping.getDDMFormValues(
				1L, ddmForm, document.asXML());

		String actualJSON = _upgradeDynamicDataMapping.toJSON(
			actualDDMFormValues);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	@Test
	public void testToXMLWithoutLocalizedData() throws Exception {
		String fieldsDisplay = "Text_INSTANCE_hcxo";

		Map<String, String> expandoValuesMap = HashMapBuilder.put(
			"Text", createLocalizationXML(new String[] {"Joe Bloggs"})
		).put(
			"_fieldsDisplay",
			createLocalizationXML(new String[] {fieldsDisplay})
		).build();

		String xml = _upgradeDynamicDataMapping.toXML(expandoValuesMap);

		Document document = SAXReaderUtil.read(xml);

		Map<String, Map<String, List<String>>> dataMap = toDataMap(document);

		Map<String, List<String>> actualTextData = dataMap.get("Text");

		assertEquals(
			ListUtil.fromArray("Joe Bloggs"), actualTextData.get("en_US"));

		Map<String, List<String>> actualFieldsDisplayData = dataMap.get(
			"_fieldsDisplay");

		assertEquals(
			ListUtil.fromArray(fieldsDisplay),
			actualFieldsDisplayData.get("en_US"));
	}

	@Test
	public void testToXMLWithRepeatableAndLocalizedData() throws Exception {
		String fieldsDisplay =
			"Text_INSTANCE_hcxo,Text_INSTANCE_vfqd,Text_INSTANCE_ycey";

		Map<String, String> expandoValuesMap = HashMapBuilder.put(
			"Text",
			createLocalizationXML(
				new String[] {"A", "B", "C"}, new String[] {"D", "E", "F"})
		).put(
			"_fieldsDisplay",
			createLocalizationXML(new String[] {fieldsDisplay})
		).build();

		String xml = _upgradeDynamicDataMapping.toXML(expandoValuesMap);

		Document document = SAXReaderUtil.read(xml);

		Map<String, Map<String, List<String>>> dataMap = toDataMap(document);

		Map<String, List<String>> actualTextData = dataMap.get("Text");

		assertEquals(
			ListUtil.fromArray("A", "B", "C"), actualTextData.get("en_US"));

		assertEquals(
			ListUtil.fromArray("D", "E", "F"), actualTextData.get("pt_BR"));

		Map<String, List<String>> actualFieldsDisplayData = dataMap.get(
			"_fieldsDisplay");

		assertEquals(
			ListUtil.fromArray(fieldsDisplay),
			actualFieldsDisplayData.get("en_US"));
	}

	protected void addDynamicContentElements(
		Element dynamicElementElement, String[] dynamicContentDataArray,
		Locale locale) {

		for (String dynamicContentData : dynamicContentDataArray) {
			Element dynamicContentElement = dynamicElementElement.addElement(
				"dynamic-content");

			dynamicContentElement.addAttribute(
				"language-id", LocaleUtil.toLanguageId(locale));
			dynamicContentElement.addCDATA(dynamicContentData);
		}
	}

	protected void addDynamicElementElement(
		Element rootElement, String fieldName,
		String[] enDynamicContentDataArray) {

		Element dynamicElementElement = createDynamicElementElement(
			rootElement, fieldName);

		addDynamicContentElements(
			dynamicElementElement, enDynamicContentDataArray, LocaleUtil.US);
	}

	protected void addDynamicElementElement(
		Element rootElement, String fieldName,
		String[] enDynamicContentDataArray,
		String[] ptDynamicContentDataArray) {

		Element dynamicElementElement = createDynamicElementElement(
			rootElement, fieldName);

		addDynamicContentElements(
			dynamicElementElement, enDynamicContentDataArray, LocaleUtil.US);
		addDynamicContentElements(
			dynamicElementElement, ptDynamicContentDataArray,
			LocaleUtil.BRAZIL);
	}

	protected void append(
		Map<String, List<String>> localizedDataMap, String languageId,
		String localizedData) {

		List<String> data = localizedDataMap.get(languageId);

		if (data == null) {
			data = new ArrayList<>();

			localizedDataMap.put(languageId, data);
		}

		data.add(localizedData);
	}

	protected void assertEquals(
		List<String> expectedDataValues, List<String> actualDataValues) {

		int expectedDataValuesSize = expectedDataValues.size();

		Assert.assertEquals(
			actualDataValues.toString(), expectedDataValuesSize,
			actualDataValues.size());

		for (int i = 0; i < expectedDataValuesSize; i++) {
			Assert.assertEquals(
				expectedDataValues.get(i), actualDataValues.get(i));
		}
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		Set<Locale> availableLocales = new LinkedHashSet<>();

		for (Locale locale : locales) {
			availableLocales.add(locale);
		}

		return availableLocales;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String instanceId, String name, Value value) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	protected Element createDynamicElementElement(
		Element rootElement, String fieldName) {

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("default-language-id", "en_US");
		dynamicElementElement.addAttribute("name", fieldName);

		return dynamicElementElement;
	}

	protected String createLocalizationXML(String[] enData) {
		StringBundler sb = new StringBundler(6);

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<root available-locales='en_US' default-locale='en_US'>");
		sb.append("<Data language-id='en_US'>");
		sb.append(StringUtil.merge(enData));
		sb.append("</Data>");
		sb.append("</root>");

		return sb.toString();
	}

	protected String createLocalizationXML(String[] enData, String[] ptData) {
		StringBundler sb = new StringBundler(10);

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<root available-locales='en_US,pt_BR,' ");
		sb.append("default-locale='en_US'>");
		sb.append("<Data language-id='en_US'>");
		sb.append(StringUtil.merge(enData));
		sb.append("</Data>");
		sb.append("<Data language-id='pt_BR'>");
		sb.append(StringUtil.merge(ptData));
		sb.append("</Data>");
		sb.append("</root>");

		return sb.toString();
	}

	protected Value createLocalizedValue(
		String enValue, String ptValue, Locale defaultLocale) {

		Value value = new LocalizedValue(defaultLocale);

		value.addString(LocaleUtil.BRAZIL, ptValue);
		value.addString(LocaleUtil.US, enValue);

		return value;
	}

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	protected Map<String, List<String>> getLocalizedDataMap(
		Element dynamicElementElement) {

		Map<String, List<String>> localizedDataMap = new HashMap<>();

		for (Element dynamicContentElement : dynamicElementElement.elements()) {
			String languageId = dynamicContentElement.attributeValue(
				"language-id");

			append(
				localizedDataMap, languageId, dynamicContentElement.getText());
		}

		return localizedDataMap;
	}

	protected String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_ddmFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	protected void setUpDDMFormValuesJSONDeserializer() throws Exception {
		field(
			DDMFormValuesJSONDeserializer.class, "_jsonFactory"
		).set(
			_ddmFormValuesDeserializer, new JSONFactoryImpl()
		);
	}

	protected void setUpDDMFormValuesJSONSerializer() throws Exception {
		field(
			DDMFormValuesJSONSerializer.class, "_jsonFactory"
		).set(
			_ddmFormValuesSerializer, new JSONFactoryImpl()
		);
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected void setUpLanguageUtil() {
		whenLanguageGetLanguageId(LocaleUtil.US, "en_US");
		whenLanguageGetLanguageId(LocaleUtil.BRAZIL, "pt_BR");

		whenLanguageGetAvailableLocalesThen(
			SetUtil.fromArray(new Locale[] {LocaleUtil.BRAZIL, LocaleUtil.US}));

		whenLanguageIsAvailableLocale(LocaleUtil.BRAZIL);
		whenLanguageIsAvailableLocale(LocaleUtil.US);

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
		LocalizationUtil localizationUtil = new LocalizationUtil();

		localizationUtil.setLocalization(new LocalizationImpl());
	}

	protected void setUpPropsValues() {
		mockStatic(PropsValues.class);
	}

	protected void setUpSAXReaderUtil() {
		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		SAXReaderImpl secureSAXReaderImpl = new SAXReaderImpl();

		secureSAXReaderImpl.setSecure(true);

		saxReaderUtil.setSAXReader(secureSAXReaderImpl);

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		SAXReaderImpl unsecureSAXReaderImpl = new SAXReaderImpl();

		unsecureSAXReaderUtil.setSAXReader(unsecureSAXReaderImpl);
	}

	protected void setUpSecureXMLFactoryProviderUtil() {
		SecureXMLFactoryProviderUtil secureXMLFactoryProviderUtil =
			new SecureXMLFactoryProviderUtil();

		secureXMLFactoryProviderUtil.setSecureXMLFactoryProvider(
			new SecureXMLFactoryProviderImpl());
	}

	protected Map<String, Map<String, List<String>>> toDataMap(
		Document document) {

		Element rootElement = document.getRootElement();

		Map<String, Map<String, List<String>>> dataMap = new HashMap<>();

		for (Element dynamicElementElement :
				rootElement.elements("dynamic-element")) {

			String name = dynamicElementElement.attributeValue("name");

			Map<String, List<String>> localizedDataMap = getLocalizedDataMap(
				dynamicElementElement);

			dataMap.put(name, localizedDataMap);
		}

		return dataMap;
	}

	protected void whenLanguageGetAvailableLocalesThen(
		Set<Locale> availableLocales) {

		when(
			_language.getAvailableLocales()
		).thenReturn(
			availableLocales
		);
	}

	protected void whenLanguageGetLanguageId(Locale locale, String languageId) {
		when(
			_language.getLanguageId(Matchers.eq(locale))
		).thenReturn(
			languageId
		);
	}

	protected void whenLanguageIsAvailableLocale(Locale locale) {
		when(
			_language.isAvailableLocale(Matchers.eq(locale))
		).thenReturn(
			true
		);
	}

	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer =
		new DDMFormValuesJSONDeserializer();
	private final DDMFormValuesSerializer _ddmFormValuesSerializer =
		new DDMFormValuesJSONSerializer();

	@Mock
	private Language _language;

	private UpgradeDynamicDataMapping _upgradeDynamicDataMapping;

}