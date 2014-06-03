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

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.LocalizationImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Pablo Carvalho
 * @author Miguel Angelo Caldas Gallindo
 */
@PrepareForTest(
	{
		HtmlUtil.class, LocaleUtil.class, LocalizationUtil.class,
		PropsUtil.class, SAXReaderUtil.class
	})
@RunWith(PowerMockRunner.class)
public class BaseDDMTest extends PowerMockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		setUpHtmlUtil();
		setUpLocaleUtil();
		setUpLocalizationUtil();
		setUpPropsUtil();
		setUpSAXReaderUtil();
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

	protected Document createSampleDocument() {
		Document document = createEmptyDocument();

		addTextElement(
			document.getRootElement(), "Unlocalizable", "Text 2", false);

		return document;
	}

	protected void setUpHtmlUtil() {
		spy(HtmlUtil.class);

		when(
			HtmlUtil.getHtml()
		).thenReturn(
			new HtmlImpl()
		);
	}

	protected void setUpLocaleUtil() {
		spy(LocaleUtil.class);

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
	}

	protected void setUpLocalizationUtil() {
		spy(LocalizationUtil.class);

		when(
			LocalizationUtil.getLocalization()
		).thenReturn(
			new LocalizationImpl()
		);
	}

	protected void setUpPropsUtil() {
		mockStatic(PropsUtil.class);

		when(
			PropsUtil.get(
				PropsKeys.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_DATATYPE)
		).thenReturn(
			"string"
		);

		when(
			PropsUtil.get(
				PropsKeys.
					DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_REPEATABLE)
		).thenReturn(
			"false"
		);

		when(
			PropsUtil.get(PropsKeys.DYNAMIC_DATA_MAPPING_IMAGE_EXTENSIONS)
		).thenReturn(
			".gif,.jpeg,.jpg,.png"
		);

		when(
			PropsUtil.get(PropsKeys.DYNAMIC_DATA_MAPPING_IMAGE_SMALL_MAX_SIZE)
		).thenReturn(
			"51200"
		);

		when(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN)
		).thenReturn(
			"yyyyMMddHHmmss"
		);
	}

	protected void setUpSAXReaderUtil() {
		spy(SAXReaderUtil.class);

		when(
			SAXReaderUtil.getSAXReader()
		).thenReturn(
			new SAXReaderImpl()
		);
	}

}