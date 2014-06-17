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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTest;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 * @author Miguel Angelo Caldas Gallindo
 */
public class DDMXMLImplTest extends BaseDDMTest {

	@Test
	public void testUpdateContentDefaultLocale() throws Exception {
		updateContentDefaultLocale("dynamic-data-mapping-structures.xml", true);
	}

	@Test
	public void testUpdateContentDefaultLocaleWrongFormat() throws Exception {
		updateContentDefaultLocale(
			"dynamic-data-mapping-structures-wrong-format.xml", false);
	}

	protected boolean checkElementLocale(Element element, String newLocaleId) {
		List<Node> nodes = element.selectNodes("dynamic-element");

		for (Node node : nodes) {
			Element metadataElement = (Element)node.selectSingleNode(
				"meta-data[@locale='" + newLocaleId + "']");

			if (metadataElement == null) {
				return false;
			}

			if (!checkElementLocale((Element)node, newLocaleId)) {
				return false;
			}
		}

		return true;
	}

	protected boolean checkLocale(Element rootElement, String newLocaleId) {
		Attribute avaliableLocalesAttribute = rootElement.attribute(
			"available-locales");

		String avaliableLocalesAttributeValue =
			avaliableLocalesAttribute.getValue();

		if (!avaliableLocalesAttributeValue.contains(newLocaleId)) {
			return false;
		}

		if (!newLocaleId.equals(rootElement.attributeValue("default-locale"))) {
			return false;
		}

		return checkElementLocale(rootElement, newLocaleId);
	}

	protected void updateContentDefaultLocale(
			String fileName, boolean expectedResult)
		throws Exception {

		String xml = read(fileName);

		Document document = SAXReaderUtil.read(xml);

		List<Node> structureNodes = document.selectNodes("//structure");

		for (Node structureNode : structureNodes) {
			String structureXML = structureNode.asXML();

			Document structureDocument = SAXReaderUtil.read(structureXML);

			Element rootElement = (Element)structureDocument.selectSingleNode(
				"/structure/root");

			Locale contentDefaultLocale = LocaleUtil.fromLanguageId(
				rootElement.attributeValue("default-locale"));

			Locale availableDefaultLocale = LocaleUtil.fromLanguageId("es_ES");

			String rootXML = rootElement.asXML();

			structureXML = _ddmXML.updateXMLDefaultLocale(
				rootXML, contentDefaultLocale, availableDefaultLocale);

			Document updatedXMLDocument = SAXReaderUtil.read(structureXML);

			rootElement = updatedXMLDocument.getRootElement();

			if (checkLocale(
					rootElement,
					LocaleUtil.toLanguageId(availableDefaultLocale))) {

				Assert.assertTrue(expectedResult);

				return;
			}
		}

		Assert.assertFalse(expectedResult);
	}

	private DDMXML _ddmXML = new DDMXMLImpl();

}