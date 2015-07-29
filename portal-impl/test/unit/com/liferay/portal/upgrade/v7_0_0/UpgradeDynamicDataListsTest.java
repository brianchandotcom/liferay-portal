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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.util.LocalizationImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.util.ArrayList;
import java.util.HashMap;
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

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@PowerMockIgnore("javax.xml.stream.*")
@RunWith(PowerMockRunner.class)
public class UpgradeDynamicDataListsTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpLanguageUtil();
		setUpLocalizationUtil();
		setUpPropsUtil();
		setUpSAXReaderUtil();
		setUpSecureXMLFactoryProviderUtil();
	}

	@Test
	public void testToXMLWithoutLocalizedData() throws Exception {
		Map<String, String> expandoValuesMap = new HashMap<>();

		expandoValuesMap.put(
			"Text", createLocalizationXML(new String[] {"Joe Bloggs"}));

		String fieldsDisplay = "Text_INSTANCE_hcxo";

		expandoValuesMap.put(
			"_fieldsDisplay",
			createLocalizationXML(new String[] {fieldsDisplay}));

		UpgradeDynamicDataLists upgradeDynamicDataLists =
			new UpgradeDynamicDataLists();

		String xml = upgradeDynamicDataLists.toXML(expandoValuesMap);

		Document document = SAXReaderUtil.read(xml);

		Map<String, Map<String, List<String>>> dataMap = toDataMap(document);

		Map<String, List<String>> actualTextData = dataMap.get("Text");

		assertEquals(
			ListUtil.toList(new String[] {"Joe Bloggs"}),
			actualTextData.get("en_US"));

		Map<String, List<String>> actualFieldsDisplayData = dataMap.get(
			"_fieldsDisplay");

		assertEquals(
			ListUtil.toList(new String[] {fieldsDisplay}),
			actualFieldsDisplayData.get("en_US"));
	}

	@Test
	public void testToXMLWithRepeatableAndLocalizedData() throws Exception {
		Map<String, String> expandoValuesMap = new HashMap<>();

		expandoValuesMap.put(
			"Text",
			createLocalizationXML(
				new String[] {"A", "B", "C"}, new String[] {"D", "E", "F"}));

		String fieldsDisplay =
			"Text_INSTANCE_hcxo,Text_INSTANCE_vfqd,Text_INSTANCE_ycey";

		expandoValuesMap.put(
			"_fieldsDisplay",
			createLocalizationXML(new String[] {fieldsDisplay}));

		UpgradeDynamicDataLists upgradeDynamicDataLists =
			new UpgradeDynamicDataLists();

		String xml = upgradeDynamicDataLists.toXML(expandoValuesMap);

		Document document = SAXReaderUtil.read(xml);

		Map<String, Map<String, List<String>>> dataMap = toDataMap(document);

		Map<String, List<String>> actualTextData = dataMap.get("Text");

		assertEquals(
			ListUtil.toList(new String[] {"A", "B", "C"}),
			actualTextData.get("en_US"));

		assertEquals(
			ListUtil.toList(new String[] {"D", "E", "F"}),
			actualTextData.get("pt_BR"));

		Map<String, List<String>> actualFieldsDisplayData = dataMap.get(
			"_fieldsDisplay");

		assertEquals(
			ListUtil.toList(new String[] {fieldsDisplay}),
			actualFieldsDisplayData.get("en_US"));
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

		Assert.assertEquals(expectedDataValuesSize, actualDataValues.size());

		for (int i = 0; i < expectedDataValuesSize; i++) {
			Assert.assertEquals(
				expectedDataValues.get(i), actualDataValues.get(i));
		}
	}

	protected String createLocalizationXML(String[] enData) {
		StringBundler sb = new StringBundler(6);

		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<root available-locales='en_US' default-locale='en_US'>");
		sb.append("<Data language-id='en_US'>");
		sb.append(StringUtil.merge(enData));
		sb.append("</Data>");
		sb.append("</root>");

		return sb.toString();
	}

	protected String createLocalizationXML(String[] enData, String[] ptData) {
		StringBundler sb = new StringBundler(10);

		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
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

	protected void setUpLanguageUtil() {
		whenLanguageGetLanguageId(LocaleUtil.BRAZIL, "pt_BR");
		whenLanguageGetLanguageId(LocaleUtil.US, "en_US");

		whenLanguageGetAvailableLocalesThen(
			SetUtil.fromArray(new Locale[] {LocaleUtil.BRAZIL, LocaleUtil.US}));

		whenLanguageIsAvailableLocale(LocaleUtil.BRAZIL);
		whenLanguageIsAvailableLocale(LocaleUtil.US);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
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

	@Mock
	private Language _language;

}