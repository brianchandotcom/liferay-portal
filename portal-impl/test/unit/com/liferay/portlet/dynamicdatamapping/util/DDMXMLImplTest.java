/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Manuel de la Peña
 */
@PrepareForTest({DDMXMLUtil.class, SAXReaderUtil.class})
@RunWith(PowerMockRunner.class)
public class DDMXMLImplTest extends PowerMockito {

	@Before
	public void setUp() {
		spy(SAXReaderUtil.class);

		when(
			SAXReaderUtil.getSAXReader()
		).thenReturn(
			_saxReader
		);

		spy(DDMXMLUtil.class);

		when(
			DDMXMLUtil.getDDMXML()
		).thenReturn(
			_ddmXML
		);
	}

	@After
	public void tearDown() {
		verifyStatic();
	}

	@Test
	public void testUpdateContentDefaultLocale() {
		updateContentDefaultLocale("dynamic-data-mapping-structures.xml");
	}

	@Test
	public void testUpdateContentDefaultLocaleWrongFormat() {
		updateContentDefaultLocale(
			"dynamic-data-mapping-structures-wrong-format.xml");
	}

	protected boolean checkElementLocale(Element parent, String newLocaleId) {
		List<Node> children = parent.selectNodes("dynamic-element");

		boolean result = true;

		for (Node child : children) {
			Element metadataElement = (Element)child.selectSingleNode(
				"meta-data[@locale='" + newLocaleId + "']");

			if (metadataElement == null) {
				return false;
			}

			result = checkElementLocale((Element) child, newLocaleId);
		}

		return result;
	}

	protected boolean checkLocale(Element rootElement, String newLocaleId) {
		Attribute avaliableLocales = rootElement.attribute("available-locales");

		if (avaliableLocales.getValue().indexOf(newLocaleId) == -1) {
			return false;
		}

		Attribute defaultLocale = rootElement.attribute("default-locale");

		if (!newLocaleId.equals(defaultLocale.getValue())) {
			return false;
		}

		return checkElementLocale(rootElement, newLocaleId);
	}

	protected String readText(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void updateContentDefaultLocale(String fileName) {
		boolean result = false;

		try {
			String xml = readText(fileName);

			Document document = SAXReaderUtil.read(xml);

			List<Node> structureNodes = document.selectNodes("//structure");

			for (Node structureNode : structureNodes) {
				String structureXML = structureNode.asXML();

				Document structureDocument = SAXReaderUtil.read(structureXML);

				Element rootElement =
					(Element)structureDocument.selectSingleNode(
						"/structure/root");

				Attribute defaultLocale = rootElement.attribute(
					"default-locale");

				Locale contentDefaultLocale = LocaleUtil.fromLanguageId(
					defaultLocale.getValue());

				Locale availableDefaultLocale = LocaleUtil.fromLanguageId(
					"es_ES");

				String rootXML = rootElement.asXML();

				structureXML = DDMXMLUtil.updateXMLDefaultLocale(
					rootXML, contentDefaultLocale, availableDefaultLocale);

				Document updatedXMLDocument = SAXReaderUtil.read(structureXML);

				rootElement = updatedXMLDocument.getRootElement();

				result = checkLocale(
					rootElement,
					LocaleUtil.toLanguageId(availableDefaultLocale));
			}

			Assert.assertTrue(result);
		}
		catch (DocumentException de) {
			Assert.fail(
				"Test failed because the Document is not well-formed: " +
					de.getMessage());
		}
		catch (IOException ioe) {
			Assert.fail(
				"Test failed because an I/O error: " + ioe.getMessage());
		}
	}

	private DDMXMLImpl _ddmXML = new DDMXMLImpl();

	private SAXReaderImpl _saxReader = new SAXReaderImpl();

}