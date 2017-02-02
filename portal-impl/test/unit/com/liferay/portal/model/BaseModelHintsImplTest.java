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

package com.liferay.portal.model;

import com.liferay.portal.kernel.model.ModelHintsCallback;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import java.net.URL;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.xml.sax.InputSource;

/**
 * @author Andrew Betts
 */
public class BaseModelHintsImplTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_testBaseModelHints = new TestBaseModelHintsImpl();

		_testBaseModelHints.afterPropertiesSet();

		_document = DocumentHelper.createDocument();

		Element rootElement = _document.addElement("model-hints");

		Element textareaHintCollectionElement = addHintCollectionElement(
			rootElement, "TEXTAREA");

		addHintElement(textareaHintCollectionElement, "display-height", "105");
		addHintElement(textareaHintCollectionElement, "display-width", "500");
		addHintElement(textareaHintCollectionElement, "max-length", "4000");

		Element modelElement = rootElement.addElement("model");

		modelElement.addAttribute("name", _MODEL);

		Element defaultHintsElement = modelElement.addElement("default-hints");

		addHintElement(defaultHintsElement, "display-width", "210");

		Element hintFieldElement = addFieldElement(
			modelElement, "hintField", "long");

		addHintElement(hintFieldElement, "type", "Date");
		addHintElement(hintFieldElement, "max-length", "150");

		Element localizedFieldElement = addFieldElement(
			modelElement, "localizedField", "String");

		localizedFieldElement.addAttribute("localized", "true");

		Element sanitizeFieldElement = addFieldElement(
			modelElement, "sanitizeField", "String");

		addSanitizeElement(sanitizeFieldElement, "text/html", "ALL");

		addFieldElement(modelElement, "simpleField", "long");

		Element validatorFieldElement = addFieldElement(
			modelElement, "validatorField", "String");

		addValidatorElement(validatorFieldElement, "required");
		addValidatorElement(validatorFieldElement, "email");

		// Type fields

		addFieldElement(modelElement, "intField", "int");
		addFieldElement(modelElement, "longField", "long");
		addFieldElement(modelElement, "doubleField", "double");
		addFieldElement(modelElement, "dateField", "Date");
		addFieldElement(modelElement, "booleanField", "boolean");
		addFieldElement(modelElement, "blobField", "Blob");
		addFieldElement(modelElement, "stringField", "String");

		// Combined Field

		Element combinedFieldElement = addFieldElement(
			modelElement, "combinedField", "String");

		combinedFieldElement.addAttribute("localized", "true");

		addHintCollectionElement(combinedFieldElement, "TEXTAREA");
		addHintElement(combinedFieldElement, "display-width", "350");
		addValidatorElement(combinedFieldElement, "required");
		addSanitizeElement(combinedFieldElement, "text/plain", "ALL");

		_testBaseModelHints.read(null, null, new TestInputStream());
	}

	@Test
	public void testBuildCustomValidatorName() {
		String custom = "custom";

		String validatorName1 = _testBaseModelHints.buildCustomValidatorName(
			custom);
		String validatorName2 = _testBaseModelHints.buildCustomValidatorName(
			custom);

		Assert.assertNotEquals(validatorName1, validatorName2);
	}

	@Test
	public void testGetDefaultHints() {
		Map<String, String> defaultHints = _testBaseModelHints.getDefaultHints(
			_MODEL);

		Assert.assertEquals(defaultHints.toString(), 1, defaultHints.size());
		Assert.assertEquals("210", defaultHints.get("display-width"));
	}

	@Test
	public void testGetFieldsElement() {
		Object fieldObject = _testBaseModelHints.getFieldsElement(
			_MODEL, "simpleField");

		if (!(fieldObject instanceof Element)) {
			Assert.fail();
		}

		Element fieldElement = (Element)fieldObject;

		Assert.assertEquals("simpleField", fieldElement.attributeValue("name"));
		Assert.assertEquals("long", fieldElement.attributeValue("type"));
	}

	@Test
	public void testGetHints() {
		Map<String, String> hints = _testBaseModelHints.getHints(
			_MODEL, "hintField");

		Assert.assertEquals("Date", hints.get("type"));
		Assert.assertEquals("150", hints.get("max-length"));

		hints = _testBaseModelHints.getHints(_MODEL, "combinedField");

		Assert.assertEquals("350", hints.get("display-width"));
	}

	@Test
	public void testGetMaxLength() {
		int maxLength = _testBaseModelHints.getMaxLength(_MODEL, "simpleField");

		Assert.assertEquals(75, maxLength);

		maxLength = _testBaseModelHints.getMaxLength(_MODEL, "hintField");

		Assert.assertEquals(150, maxLength);

		maxLength = _testBaseModelHints.getMaxLength(_MODEL, "combinedField");

		Assert.assertEquals(4000, maxLength);
	}

	@Test
	public void testGetModels() {
		List<String> models = _testBaseModelHints.getModels();

		Assert.assertEquals(models.toString(), 1, models.size());

		Assert.assertEquals(_MODEL, models.get(0));
	}

	@Test
	public void testGetSanitizeTuple() {
		Tuple sanitizeTuple = _testBaseModelHints.getSanitizeTuple(
			_MODEL, "simpleField");

		Assert.assertNull(sanitizeTuple);

		sanitizeTuple = _testBaseModelHints.getSanitizeTuple(
			_MODEL, "sanitizeField");

		Assert.assertEquals(3, sanitizeTuple.getSize());
		Assert.assertEquals("sanitizeField", sanitizeTuple.getObject(0));
		Assert.assertEquals("text/html", sanitizeTuple.getObject(1));
		Assert.assertEquals("ALL", sanitizeTuple.getObject(2));
	}

	@Test
	public void testGetSanitizeTuples() {
		List<Tuple> sanitizedTuples = _testBaseModelHints.getSanitizeTuples(
			_MODEL);

		Assert.assertEquals(
			sanitizedTuples.toString(), 2, sanitizedTuples.size());

		Tuple sanitizeFieldTuple = sanitizedTuples.get(0);

		Assert.assertEquals("sanitizeField", sanitizeFieldTuple.getObject(0));
		Assert.assertEquals("text/html", sanitizeFieldTuple.getObject(1));
		Assert.assertEquals("ALL", sanitizeFieldTuple.getObject(2));

		Tuple combinedFieldTuple = sanitizedTuples.get(1);

		Assert.assertEquals("combinedField", combinedFieldTuple.getObject(0));
		Assert.assertEquals("text/plain", combinedFieldTuple.getObject(1));
		Assert.assertEquals("ALL", combinedFieldTuple.getObject(2));
	}

	@Test
	public void testGetType() {
		Assert.assertEquals(
			"Blob", _testBaseModelHints.getType(_MODEL, "blobField"));
		Assert.assertEquals(
			"boolean", _testBaseModelHints.getType(_MODEL, "booleanField"));
		Assert.assertEquals(
			"Date", _testBaseModelHints.getType(_MODEL, "dateField"));
		Assert.assertEquals(
			"double", _testBaseModelHints.getType(_MODEL, "doubleField"));
		Assert.assertEquals(
			"int", _testBaseModelHints.getType(_MODEL, "intField"));
		Assert.assertEquals(
			"long", _testBaseModelHints.getType(_MODEL, "longField"));
		Assert.assertEquals(
			"String", _testBaseModelHints.getType(_MODEL, "stringField"));
	}

	@Test
	public void testGetValidators() {
		List<Tuple> validators = _testBaseModelHints.getValidators(
			_MODEL, "simpleField");

		Assert.assertNull(validators);

		validators = _testBaseModelHints.getValidators(
			_MODEL, "validatorField");

		Assert.assertEquals(validators.toString(), 2, validators.size());

		Tuple emailValidator = validators.get(0);

		Assert.assertEquals("validatorField", emailValidator.getObject(0));
		Assert.assertEquals("email", emailValidator.getObject(1));

		Tuple requiredValidator = validators.get(1);

		Assert.assertEquals("validatorField", requiredValidator.getObject(0));
		Assert.assertEquals("required", requiredValidator.getObject(1));

		validators = _testBaseModelHints.getValidators(_MODEL, "combinedField");

		Assert.assertEquals(validators.toString(), 1, validators.size());

		requiredValidator = validators.get(0);

		Assert.assertEquals("combinedField", requiredValidator.getObject(0));
		Assert.assertEquals("required", requiredValidator.getObject(1));
	}

	@Test
	public void testGetValue() {
		String type = _testBaseModelHints.getType(_MODEL, "simpleField");

		String value = _testBaseModelHints.getValue(
			_MODEL, "simpleField", "type", type);

		Assert.assertEquals("long", type);

		Assert.assertEquals("long", value);

		type = _testBaseModelHints.getType(_MODEL, "hintField");

		value = _testBaseModelHints.getValue(_MODEL, "hintField", "type", type);

		Assert.assertEquals("long", type);

		Assert.assertEquals("Date", value);
	}

	@Test
	public void testHasField() {
		Assert.assertFalse(_testBaseModelHints.hasField(_MODEL, "test"));

		Assert.assertTrue(_testBaseModelHints.hasField(_MODEL, "simpleField"));
	}

	@Test
	public void testIsCustomValidator() {
		Assert.assertFalse(_testBaseModelHints.isCustomValidator("test"));

		Assert.assertTrue(_testBaseModelHints.isCustomValidator("custom"));
	}

	@Test
	public void testIsLocalized() {
		Assert.assertFalse(
			_testBaseModelHints.isLocalized(_MODEL, "simpleField"));
		Assert.assertTrue(
			_testBaseModelHints.isLocalized(_MODEL, "localizedField"));
	}

	@Test
	public void testTrimString() {
		String value = RandomTestUtil.randomString(400);

		value = _testBaseModelHints.trimString(_MODEL, "hintField", value);

		Assert.assertEquals(150, value.length());
	}

	protected static Element addFieldElement(
		Element parentElement, String name, String type) {

		Element fieldElement = parentElement.addElement("field");

		fieldElement.addAttribute("name", name);
		fieldElement.addAttribute("type", type);

		return fieldElement;
	}

	protected static Element addHintCollectionElement(
		Element parentElement, String name) {

		Element hintCollectionElement = parentElement.addElement(
			"hint-collection");

		hintCollectionElement.addAttribute("name", name);

		return hintCollectionElement;
	}

	protected static Element addHintElement(
		Element parentElement, String name, String value) {

		Element hintElement = parentElement.addElement("hint");

		hintElement.addAttribute("name", name);
		hintElement.setText(value);

		return hintElement;
	}

	protected static Element addSanitizeElement(
		Element parentElement, String contentType, String modes) {

		Element sanitizeElement = parentElement.addElement("sanitize");

		sanitizeElement.addAttribute("content-type", contentType);
		sanitizeElement.addAttribute("modes", modes);

		return sanitizeElement;
	}

	protected static Element addValidatorElement(
		Element parentElement, String name) {

		Element validatorElement = parentElement.addElement("validator");

		validatorElement.addAttribute("name", name);

		return validatorElement;
	}

	private static final String _MODEL =
		"com.liferay.portal.model.BaseModelHintsImplTest";

	private static Document _document;
	private static TestBaseModelHintsImpl _testBaseModelHints;

	private static class TestBaseModelHintsImpl extends BaseModelHintsImpl {

		@Override
		public ModelHintsCallback getModelHintsCallback() {
			return new ModelHintsCallback() {

				@Override
				public void execute(ClassLoader classLoader, String name) {
				}

			};
		}

		@Override
		public String[] getModelHintsConfigs() {
			return new String[0];
		}

		@Override
		public SAXReader getSAXReader() {
			return new TestSAXReader();
		}

	}

	private static class TestInputStream extends InputStream {

		@Override
		public int read() {
			return 0;
		}

	}

	private static class TestSAXReader extends SAXReader {

		@Override
		public Document read(File file) {
			return _document;
		}

		@Override
		public Document read(InputSource in) {
			return _document;
		}

		@Override
		public Document read(InputStream in) {
			return _document;
		}

		@Override
		public Document read(InputStream in, String systemId) {
			return _document;
		}

		@Override
		public Document read(Reader reader) {
			return _document;
		}

		@Override
		public Document read(Reader reader, String systemId) {
			return _document;
		}

		@Override
		public Document read(String systemId) {
			return _document;
		}

		@Override
		public Document read(URL url) {
			return _document;
		}

	}

}