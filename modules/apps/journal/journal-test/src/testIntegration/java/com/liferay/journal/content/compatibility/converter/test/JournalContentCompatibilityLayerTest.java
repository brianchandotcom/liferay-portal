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

package com.liferay.journal.content.compatibility.converter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.content.compatibility.converter.JournalContentCompatibilityLayer;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class JournalContentCompatibilityLayerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAllFieldsCompatibilityLayer() throws Exception {
		String content = read(
			"test-journal-content-all-fields-compatibility.xml");

		Document document = SAXReaderUtil.read(content);

		_journalContentCompatibilityLayer.convert(document);

		String expectedContent = read(
			"test-journal-content-all-fields-compatibility-expected-" +
				"results.xml");

		Document expectedDocument = SAXReaderUtil.read(expectedContent);

		Assert.assertEquals(
			_getFormattedString(expectedDocument),
			_getFormattedString(document));
	}

	@Test
	public void testComplexNestedFieldsCompatibilityLayer() throws Exception {
		String content = read(
			"test-journal-content-complex-nested-fields-compatibility.xml");

		Document document = SAXReaderUtil.read(content);

		_journalContentCompatibilityLayer.convert(document);

		String expectedContent = read(
			"test-journal-content-complex-nested-fields-compatibility-" +
				"expected-results.xml");

		Document expectedDocument = SAXReaderUtil.read(expectedContent);

		Assert.assertEquals(
			_getFormattedString(expectedDocument),
			_getFormattedString(document));
	}

	@Test
	public void testNestedFieldsCompatibilityLayer() throws Exception {
		String content = read(
			"test-journal-content-nested-fields-compatibility.xml");

		Document document = SAXReaderUtil.read(content);

		_journalContentCompatibilityLayer.convert(document);

		String expectedContent = read(
			"test-journal-content-nested-fields-compatibility-expected-" +
				"results.xml");

		Document expectedDocument = SAXReaderUtil.read(expectedContent);

		Assert.assertEquals(
			_getFormattedString(expectedDocument),
			_getFormattedString(document));
	}

	@Test
	public void testRepeatableNestedFieldsCompatibilityLayer()
		throws Exception {

		String content = read(
			"test-journal-content-repeatable-nested-fields-compatibility.xml");

		Document document = SAXReaderUtil.read(content);

		_journalContentCompatibilityLayer.convert(document);

		String expectedContent = read(
			"test-journal-content-repeatable-nested-fields-compatibility-" +
				"expected-results.xml");

		Document expectedDocument = SAXReaderUtil.read(expectedContent);

		Assert.assertEquals(
			_getFormattedString(expectedDocument),
			_getFormattedString(document));
	}

	@Test
	public void testVersionCompatibilityLayer() throws Exception {
		String content = read("test-journal-content-version-compatibility.xml");

		Document document = SAXReaderUtil.read(content);

		_journalContentCompatibilityLayer.convert(document);

		String expectedContent = read(
			"test-journal-content-version-compatibility-expected-results.xml");

		Document expectedDocument = SAXReaderUtil.read(expectedContent);

		Assert.assertEquals(
			_getFormattedString(expectedDocument),
			_getFormattedString(document));
	}

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/journal/dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private String _getFormattedString(Document document) throws Exception {
		String formattedString = document.formattedString();

		return formattedString.replaceAll("instance-id=\"[a-zA-Z0-9]+\"", "");
	}

	@Inject
	private JournalContentCompatibilityLayer _journalContentCompatibilityLayer;

}