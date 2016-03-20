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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Inácio Nery
 * @author Rafael Praxedes
 */
public class UpgradeJournalTest {

	@Before
	public void setUp() throws Exception {
		setUpHtmlUtil();
		setUpHttpUtil();
		setUpPropsUtil();
		setUpSAXReaderUtil();
	}

	@Test
	public void testGetDDMXSD() throws Exception {
		String expectedDDMXSD = read("test-ddm-structure-all-fields.xml");

		UpgradeJournal upgradeJournal = new UpgradeJournal();

		upgradeJournal.setUpStrutureAttributesMappings();

		String journalXSD = read("test-journal-structure-all-fields.xml");

		String actualDDMXSD = upgradeJournal.getDDMXSD(
			journalXSD, LocaleUtil.getSiteDefault());

		Element expectedRootElement = getRootElement(expectedDDMXSD);
		Element actualRootElement = getRootElement(actualDDMXSD);

		List<Element> elements = new ArrayList<>(1);

		elements.add(actualRootElement);

		Assert.assertTrue(containsElement(expectedRootElement, elements));
	}

	protected boolean containsElement(
		Element expectedElement, List<Element> actualTestedElementList) {

		String[] ignoredAttributes = getIgnoredAttributes(expectedElement);

		for (Element childActualTestedElement : actualTestedElementList) {
			if (matchElement(
					expectedElement, childActualTestedElement,
					ignoredAttributes)) {

				for (Element childExpectedElement :
						expectedElement.elements()) {

					if (!containsElement(
							childExpectedElement,
							childActualTestedElement.elements())) {

						return false;
					}
				}

				return true;
			}
		}

		return false;
	}

	protected String[] getIgnoredAttributes(Element expectedElement) {
		List<String> ignoredAttributes = new ArrayList<>();

		if (expectedElement.getName().equals("dynamic-element")) {
			Attribute type = expectedElement.attribute("type");

			if ((type != null) && type.getValue().equals("option")) {
				ignoredAttributes.add("name");
			}
		}

		return ignoredAttributes.toArray(new String[0]);
	}

	protected Element getRootElement(String xml) throws Exception {
		Document document = SAXReaderUtil.read(xml);

		return document.getRootElement();
	}

	protected boolean matchAttribute(
		Attribute expectedAttribute, List<Attribute> actualAttributeList,
		String... ignoredAttributes) {

		if (ArrayUtil.contains(
				ignoredAttributes, expectedAttribute.getName())) {

			return true;
		}

		for (Attribute actualAttribute : actualAttributeList) {
			if (ArrayUtil.contains(
					ignoredAttributes, actualAttribute.getName())) {

				continue;
			}

			if (Validator.equals(
					actualAttribute.getName(), expectedAttribute.getName()) &&
				Validator.equals(
					actualAttribute.getValue(), expectedAttribute.getValue())) {

				return true;
			}
		}

		return false;
	}

	protected boolean matchElement(
		Element expectedElement, Element actualElement,
		String... ignoredAttributes) {

		if (expectedElement.attributeCount() !=
				actualElement.attributeCount()) {

			return false;
		}

		for (Attribute expectedAttribute : expectedElement.attributes()) {
			if (!matchAttribute(
					expectedAttribute, actualElement.attributes(),
					ignoredAttributes)) {

				return false;
			}
		}

		if (ListUtil.isEmpty(expectedElement.elements())) {
			if (Validator.equals(
					expectedElement.getStringValue(),
					actualElement.getStringValue())) {

				return true;
			}
		}

		return true;
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	protected void setUpHttpUtil() {
		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());
	}

	protected void setUpPropsUtil() {
		PropsUtil.setProps(new PropsImpl());
	}

	protected void setUpSAXReaderUtil() {
		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		SAXReaderImpl secureSAXReader = new SAXReaderImpl();

		secureSAXReader.setSecure(true);

		saxReaderUtil.setSAXReader(secureSAXReader);

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		unsecureSAXReaderUtil.setSAXReader(new SAXReaderImpl());
	}

}