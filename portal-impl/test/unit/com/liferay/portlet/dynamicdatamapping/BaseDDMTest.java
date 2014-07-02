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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
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
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateImpl;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Pablo Carvalho
 * @author Miguel Angelo Caldas Gallindo
 */
@RunWith(PowerMockRunner.class)
public abstract class BaseDDMTest extends PowerMockito {

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

	protected DDMStructure createStructure(String name, Document document) {
		DDMStructure structure = new DDMStructureImpl();

		structure.setStructureId(RandomTestUtil.randomLong());
		structure.setName(name);
		structure.setDefinition(document.asXML());

		_structures.put(structure.getStructureId(), structure);

		return structure;
	}

	protected DDMStructure createStructure(String name, String... fieldNames) {
		Document document = createDocument(fieldNames);

		return createStructure(name, document);
	}

	protected DDMTemplate createTemplate(
		long templateId, String name, String script) {

		DDMTemplate template = new DDMTemplateImpl();

		template.setTemplateId(templateId);
		template.setName(name);
		template.setScript(script);

		_templates.put(template.getTemplateId(), template);

		return template;
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

					return _structures.get(structureId);
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

					return _templates.get(templateId);
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
		mockStatic(LanguageUtil.class);

		when(
			LanguageUtil.isAvailableLanguageCode("en_US")
		).thenReturn(
			true
		);

		when(
			LanguageUtil.isAvailableLanguageCode("pt_BR")
		).thenReturn(
			true
		);
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
		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		saxReaderUtil.setSAXReader(new SAXReaderImpl());
	}

	private Map<Long, DDMStructure> _structures =
		new HashMap<Long, DDMStructure>();
	private Map<Long, DDMTemplate> _templates =
		new HashMap<Long, DDMTemplate>();

}