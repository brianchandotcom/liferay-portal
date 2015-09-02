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

package com.liferay.dynamic.data.mapping.upgrade.v1_0_0;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.io.internal.DDMFormValuesJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.util.LocalizationImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({DDMFormValuesJSONSerializerUtil.class, LocaleUtil.class})
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	"com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializerUtil"
)
public class UpgradeDynamicDataMappingTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpDDMFormValuesJSONSerializerUtil();
		setUpLanguageUtil();
		setUpLocaleUtil();
		setUpLocalizationUtil();
		setUpPropsUtil();
		setUpSecureXMLFactoryProviderUtil();
		setUpSAXReaderUtil();
		setUpJSONFactoryUtil();
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
			new String[] {"Pt Text Value 1", "Pt Text Value 2"}
		);
		addDynamicElementElement(
			rootElement, "TextArea",
			new String[] {
				"En Text Area Value 1", "En Text Area Value 2",
				"En Text Area Value 3"
			},
			new String[] {
				"Pt Text Area Value 1", "Pt Text Area Value 2",
				"Pt Text Area Value 3"
			}
		);
		addDynamicElementElement(
			rootElement, "_fieldsDisplay",
			new String[] {
				"Text_INSTANCE_srfa,TextArea_INSTANCE_elcy," +
				"TextArea_INSTANCE_uxyj,Text_INSTANCE_ealq," +
				"TextArea_INSTANCE_eepy"
			}
		);

		String expectedJSON = DDMFormValuesJSONSerializerUtil.serialize(
			ddmFormValues);

		UpgradeDynamicDataMapping upgradeDynamicDataMapping =
			new UpgradeDynamicDataMapping();

		DDMFormValues actualDDMFormValues =
			upgradeDynamicDataMapping.getDDMFormValues(
				ddmForm, document.asXML());

		String actualJSON = upgradeDynamicDataMapping.toJSON(
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
			new String[] {"Pt Text Value 1", "Pt Text Value 2"}
		);
		addDynamicElementElement(
			rootElement, "TextArea",
			new String[] {
				"En Text Area Value 1", "En Text Area Value 2",
				"En Text Area Value 3"
			},
			new String[] {
				"Pt Text Area Value 1", "Pt Text Area Value 2",
				"Pt Text Area Value 3"
			}
		);
		addDynamicElementElement(
			rootElement, "Integer", new String[] {"1"}
		);
		addDynamicElementElement(
			rootElement, "_fieldsDisplay",
			new String[] {
				"Text_INSTANCE_srfa,Text_INSTANCE_ealq," +
				"TextArea_INSTANCE_elcy,TextArea_INSTANCE_uxyj," +
				"TextArea_INSTANCE_eepy,Integer_INSTANCE_ckkp"
			}
		);

		String expectedJSON = DDMFormValuesJSONSerializerUtil.serialize(
			ddmFormValues);

		UpgradeDynamicDataMapping upgradeDynamicDataMapping =
			new UpgradeDynamicDataMapping();

		DDMFormValues actualDDMFormValues =
			upgradeDynamicDataMapping.getDDMFormValues(
				ddmForm, document.asXML());

		String actualJSON = upgradeDynamicDataMapping.toJSON(
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
			rootElement, "Text", new String[] {"Text Value"}
		);
		addDynamicElementElement(
			rootElement, "TextArea",
			new String[] {
				"Text Area Value 1", "Text Area Value 2", "Text Area Value 3"
			}
		);
		addDynamicElementElement(
			rootElement, "_fieldsDisplay",
			new String[] {
				"Text_INSTANCE_hcxo,TextArea_INSTANCE_vfqd," +
				"TextArea_INSTANCE_ycey,TextArea_INSTANCE_habt"
			}
		);

		String expectedJSON = DDMFormValuesJSONSerializerUtil.serialize(
			ddmFormValues);

		UpgradeDynamicDataMapping upgradeDynamicDataMapping =
			new UpgradeDynamicDataMapping();

		DDMFormValues actualDDMFormValues =
			upgradeDynamicDataMapping.getDDMFormValues(
				ddmForm, document.asXML());

		String actualJSON = upgradeDynamicDataMapping.toJSON(
			actualDDMFormValues);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
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

	protected Value createLocalizedValue(
		String enValue, String ptValue, Locale defaultLocale) {

		Value value = new LocalizedValue(defaultLocale);

		value.addString(LocaleUtil.BRAZIL, ptValue);
		value.addString(LocaleUtil.US, enValue);

		return value;
	}

	protected void setUpDDMFormValuesJSONSerializerUtil() {
		mockStatic(DDMFormValuesJSONSerializerUtil.class);

		when(
			DDMFormValuesJSONSerializerUtil.getDDMFormValuesJSONSerializer()
		).thenReturn(
			new DDMFormValuesJSONSerializerImpl()
		);

		when(
			DDMFormValuesJSONSerializerUtil.serialize(
				Matchers.any(DDMFormValues.class))
		).thenCallRealMethod();
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

	protected void setUpPropsUtil() {
		Props props = mock(Props.class);

		when(
			props.get(PropsKeys.XML_SECURITY_ENABLED)
		).thenReturn(
			Boolean.TRUE.toString()
		);

		PropsUtil.setProps(props);
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

	protected void setUpSecureXMLFactoryProviderUtil() {
		SecureXMLFactoryProviderUtil secureXMLFactoryProviderUtil =
			new SecureXMLFactoryProviderUtil();

		secureXMLFactoryProviderUtil.setSecureXMLFactoryProvider(
			new SecureXMLFactoryProviderImpl());
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

	@Mock
	private Language _language;

}