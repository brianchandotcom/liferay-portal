/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockPageContext;

/**
 * @author Pablo Carvalho
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DDMXSDImplTest {
	@Before
	public void setUp() {
		_ddmXSDImpl = new DDMXSDImpl();
		_mockPageContext = new MockPageContext();

		_portletNamespace = "portletNamespace.";
		_namespace = "namespace.";
		_fieldsContextKey = _portletNamespace + _namespace + "fieldsContext";

		createDocument();
	}

	@Test
	public void testGetFieldsContext() throws Exception {
		_ddmXSDImpl.getFieldsContext(
				_mockPageContext, _portletNamespace, _namespace);

		Assert.assertNotNull(_mockPageContext.getAttribute(_fieldsContextKey));
	}

	@Test
	public void testGetLocalizableFieldContext() throws Exception {
		Element root = _document.getRootElement();

		Element field = addTextElement(root, "Localizable", "Loc", true);

		Locale locale = LocaleUtil.US;

		Map<String, Object> fieldContext = _ddmXSDImpl.getFieldContext(
			_mockPageContext, "portlet", "namespace", field, locale);

		Assert.assertFalse(fieldContext.containsKey("disabled"));
	}

	@Test
	public void testGetTranslatingLocalizableFieldContext() throws Exception {
		Element root = _document.getRootElement();

		Element field = addTextElement(root, "Localizable", "Loc", true);

		Locale locale = LocaleUtil.BRAZIL;

		Map<String, Object> fieldContext = _ddmXSDImpl.getFieldContext(
			_mockPageContext, "portlet", "namespace", field, locale);

		Assert.assertFalse(fieldContext.containsKey("disabled"));
	}

	@Test
	public void testGetTranslatingUnlocalizableFieldContext() throws Exception {
		Element root = _document.getRootElement();

		Element field = addTextElement(root, "Unlocalizable", "Unloc", false);

		Locale locale = LocaleUtil.BRAZIL;

		Map<String, Object> fieldContext = _ddmXSDImpl.getFieldContext(
			_mockPageContext, "portlet", "namespace", field, locale);

		Assert.assertEquals(
			Boolean.TRUE.toString(), fieldContext.get("disabled"));
	}

	@Test
	public void testGetUnlocalizableFieldContext() throws Exception {
		Element root = _document.getRootElement();

		Element field = addTextElement(root, "Unlocalizable", "Unloc", false);

		Locale locale = LocaleUtil.US;

		Map<String, Object> fieldContext = _ddmXSDImpl.getFieldContext(
			_mockPageContext, "portlet", "namespace", field, locale);

		Assert.assertFalse(fieldContext.containsKey("disabled"));
	}

	protected Element addTextElement(
			Element element, String name, String label, boolean localizable) {
		Element dynamicElement = element.addElement("dynamic-element");

		dynamicElement.addAttribute("dataType", "string");
		dynamicElement.addAttribute("name", name);
		dynamicElement.addAttribute("type", "text");
		dynamicElement.addAttribute("localizable", String.valueOf(localizable));

		Element metadataElement = dynamicElement.addElement("meta-data");

		Locale locale = LocaleUtil.US;
		metadataElement.addAttribute("locale", LocaleUtil.toLanguageId(locale));

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", "label");
		entryElement.setText(label);

		return dynamicElement;
	}

	protected void createDocument() {
		_document = SAXReaderUtil.createDocument();

		Element rootElement = _document.addElement("root");

		rootElement.addAttribute("available-locales", "en_US");
		rootElement.addAttribute("default-locale", "en_US");

		addTextElement(rootElement, "Unlocalizable", "Text 2", false);
	}

	private DDMXSDImpl _ddmXSDImpl;
	private Document _document;
	private String _fieldsContextKey;
	private MockPageContext _mockPageContext;
	private String _namespace;
	private String _portletNamespace;

}