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
import com.liferay.portlet.dynamicdatamapping.util.DDMFormXSDSerializer;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormXSDSerializerImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormXSDSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import org.junit.Before;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Pablo Carvalho
 * @author Miguel Angelo Caldas Gallindo
 */
@PrepareForTest( {
	DDMFormXSDSerializerUtil.class, DDMXMLUtil.class, HtmlUtil.class,
	LocaleUtil.class, LocalizationUtil.class, PropsUtil.class,
	SAXReaderUtil.class
})
@RunWith(PowerMockRunner.class)
public class BaseDDMTest extends PowerMockito {

	public BaseDDMTest() {

		super();
	}

	@Before
	public void setUp() throws Exception {

		setUpMocks();

		_document = createSampleDocument();

		_fieldsContextKey = _PORTLET_NAMESPACE.concat(
			_NAMESPACE).concat("fieldsContext");
	}

	protected Element addTextElement(Element element, String name, String label,
	boolean localizable) {

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

	protected Document createSampleDocument() {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", "en_US");
		rootElement.addAttribute("default-locale", "en_US");

		addTextElement(rootElement, "Unlocalizable", "Text 2", false);

		return document;
	}

	protected void setUpDDMFormXSDSerializer() {

		spy(DDMFormXSDSerializerUtil.class);

		when(DDMFormXSDSerializerUtil.getDDMFormXSDSerializer()).thenReturn(
			_ddmFormXSDSerializer);
	}

	protected void setUpDDMXMLUtil() {

		spy(DDMXMLUtil.class);

		when(DDMXMLUtil.getDDMXML()).thenReturn(_ddmXML);
	}

	protected void setUpHtml() {

		spy(HtmlUtil.class);

		when(HtmlUtil.getHtml()).thenReturn(new HtmlImpl());
	}

	protected void setUpLocale() {

		spy(LocaleUtil.class);

		when(LocaleUtil.fromLanguageId("en_US")).thenReturn(LocaleUtil.US);

		when(LocaleUtil.fromLanguageId("pt_BR")).thenReturn(LocaleUtil.BRAZIL);

		when(LocaleUtil.getSiteDefault()).thenReturn(LocaleUtil.US);
	}

	protected void setUpLocalizationUtil() {

		spy(LocalizationUtil.class);

		when(LocalizationUtil.getLocalization()).thenReturn(
			new LocalizationImpl());
	}

	protected void setUpMocks() {

		setUpDDMFormXSDSerializer();
		setUpDDMXMLUtil();
		setUpHtml();
		setUpLocale();
		setUpLocalizationUtil();
		setUpPropsUtil();
		setUpSAXReader();
	}

	protected void setUpPropsUtil() {

		mockStatic(PropsUtil.class);

		when(PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN)).thenReturn(
			"yyyyMMddHHmmss");

		when(
			PropsUtil.get(
				PropsKeys.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_DATATYPE)
			).thenReturn("string");

		when(
			PropsUtil.get(
				PropsKeys.
				DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_REPEATABLE)
		).thenReturn("false");

		when(
			PropsUtil.get(PropsKeys.DYNAMIC_DATA_MAPPING_IMAGE_EXTENSIONS)
		).thenReturn(".gif,.jpeg,.jpg,.png");

		when(
			PropsUtil.get(PropsKeys.DYNAMIC_DATA_MAPPING_IMAGE_SMALL_MAX_SIZE)
		).thenReturn("51200");
	}

	protected void setUpSAXReader() {

		spy(SAXReaderUtil.class);

		when(SAXReaderUtil.getSAXReader()).thenReturn(new SAXReaderImpl());
	}

	protected static final String _NAMESPACE = "_namespace_";

	protected static final String _PORTLET_NAMESPACE = "_portletNamespace_";

	protected DDMFormXSDSerializer _ddmFormXSDSerializer =
		new DDMFormXSDSerializerImpl();
	protected DDMXMLImpl _ddmXML = new DDMXMLImpl();
	protected Document _document;
	protected String _fieldsContextKey;
}