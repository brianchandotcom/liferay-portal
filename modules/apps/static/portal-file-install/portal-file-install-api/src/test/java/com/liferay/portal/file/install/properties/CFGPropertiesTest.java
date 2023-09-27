/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.properties;

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class CFGPropertiesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testException() throws IOException {
		try {
			CFGProperties cfgProperties = new CFGProperties();

			cfgProperties.load(null);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
		}

		try {
			CFGProperties cfgProperties = new CFGProperties();

			cfgProperties.load(new UnsyncBufferedReader(null));

			Assert.fail();
		}
		catch (IOException ioException) {
			Assert.assertEquals("Reader is null", ioException.getMessage());
		}
	}

	@Test
	public void testKeySet() throws IOException {
		CFGProperties cfgProperties = _createCFGProperties(
			"testKey1=testValue1\ntestKey2=testValue2");

		Assert.assertEquals(
			new HashSet<>(Arrays.asList("testKey1", "testKey2")),
			cfgProperties.keySet());
	}

	@Test
	public void testLoadAndSave() throws IOException {
		String line = "testKey=testValue";

		CFGProperties cfgProperties = _createCFGProperties(line);

		Assert.assertEquals("testValue", cfgProperties.get("testKey"));

		_assertSave(cfgProperties, line);
	}

	@Test
	public void testLoadAndSaveEmpty() throws IOException {
		_assertSave(new CFGProperties(), StringPool.BLANK);
	}

	@Test
	public void testLoadAndSaveMultiline() throws IOException {
		String line = "testKey1=testValue11\\\n\ttestValue12";

		CFGProperties cfgProperties = _createCFGProperties(line);

		Assert.assertEquals(
			"testValue11testValue12", cfgProperties.get("testKey1"));

		_assertSave(cfgProperties, line);
	}

	@Test
	public void testLoadAndSaveMultiple() throws IOException {
		String line = "testKey1=testValue1\ntestKey2=testValue2";

		CFGProperties cfgProperties = _createCFGProperties(line);

		Assert.assertEquals("testValue1", cfgProperties.get("testKey1"));
		Assert.assertEquals("testValue2", cfgProperties.get("testKey2"));

		_assertSave(cfgProperties, line);
	}

	@Test
	public void testLoadAndSaveMultipleWithComment() throws IOException {
		String line =
			"#comment1\ntestKey1=testValue1\n!comment2\ntestKey2=testValue2";

		CFGProperties cfgProperties = _createCFGProperties(line);

		Assert.assertEquals("testValue1", cfgProperties.get("testKey1"));
		Assert.assertEquals("testValue2", cfgProperties.get("testKey2"));

		_assertSave(cfgProperties, line);
	}

	@Test
	public void testLoadAndSaveWithBlankLine() throws IOException {
		String line = "testKey1=testValue1\n\ntestKey2=testValue2";

		CFGProperties cfgProperties = _createCFGProperties(line);

		Assert.assertEquals("testValue1", cfgProperties.get("testKey1"));
		Assert.assertEquals("testValue2", cfgProperties.get("testKey2"));

		_assertSave(cfgProperties, line);
	}

	@Test
	public void testLoadAndSaveWithComment() throws IOException {
		String line = "#comment\ntestKey=testValue";

		CFGProperties cfgProperties = _createCFGProperties(line);

		Assert.assertEquals("testValue", cfgProperties.get("testKey"));

		_assertSave(cfgProperties, line);
	}

	@Test
	public void testLoadConfigFormat() throws IOException {
		String line = "testKey1=T\"testValue1\"";

		try {
			_createCFGProperties(line);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Detected .config format in .cfg file in line: " + line,
				illegalArgumentException.getMessage());
		}
	}

	@NewEnv(type = NewEnv.Type.JVM)
	@NewEnv.Environment(variables = "LIFERAY_FOO_ENV_VALUE=ENV_TEST_VALUE")
	@Test
	public void testLoadEnvVariable() throws IOException {
		String line = "testKey=${LIFERAY_FOO_ENV_VALUE}";

		CFGProperties cfgProperties = _createCFGProperties(line);

		Assert.assertEquals("ENV_TEST_VALUE", cfgProperties.get("testKey"));

		_assertSave(cfgProperties, line);
	}

	@Test
	public void testLoadPutAndSave() throws IOException {
		CFGProperties cfgProperties = _createCFGProperties(
			"testKey1=testValue1");

		cfgProperties.put("testKey2", "testValue2");

		Assert.assertEquals("testValue1", cfgProperties.get("testKey1"));
		Assert.assertEquals("testValue2", cfgProperties.get("testKey2"));

		_assertSave(cfgProperties, "testKey1=testValue1\ntestKey2=testValue2");
	}

	@Test
	public void testLoadRemoveAndSave() throws IOException {
		CFGProperties cfgProperties = _createCFGProperties(
			"testKey1=testValue1\ntestKey2=testValue2");

		cfgProperties.remove("testKey1");

		Assert.assertNull(cfgProperties.get("testKey1"));
		Assert.assertEquals("testValue2", cfgProperties.get("testKey2"));

		_assertSave(cfgProperties, "testKey2=testValue2");
	}

	@Test
	public void testPutAndSaveArray() throws IOException {
		CFGProperties cfgProperties = new CFGProperties();

		String[] array = {};

		cfgProperties.put("testKey1", array);

		Assert.assertEquals(StringPool.BLANK, cfgProperties.get("testKey1"));

		array = new String[] {"testValue1", "testValue2"};

		cfgProperties.put("testKey1", array);

		String arrayString = "testValue1,testValue2";

		Assert.assertEquals(arrayString, cfgProperties.get("testKey1"));

		_assertSave(cfgProperties, "testKey1=" + arrayString);
	}

	@Test
	public void testPutAndSaveCollection() throws IOException {
		CFGProperties cfgProperties = new CFGProperties();

		cfgProperties.put("testKey1", Collections.emptyList());

		Assert.assertEquals(StringPool.BLANK, cfgProperties.get("testKey1"));

		cfgProperties.put(
			"testKey1", Arrays.asList("testValue1", "testValue2"));

		String listString = "testValue1,testValue2";

		Assert.assertEquals(listString, cfgProperties.get("testKey1"));

		_assertSave(cfgProperties, "testKey1=" + listString);
	}

	private void _assertSave(CFGProperties cfgProperties, String expected)
		throws IOException {

		try (StringWriter stringWriter = new StringWriter()) {
			cfgProperties.save(stringWriter);

			Assert.assertEquals(expected, stringWriter.toString());
		}
	}

	private CFGProperties _createCFGProperties(String string)
		throws IOException {

		CFGProperties cfgProperties = new CFGProperties();

		try (StringReader stringReader = new StringReader(string)) {
			cfgProperties.load(stringReader);
		}

		return cfgProperties;
	}

}