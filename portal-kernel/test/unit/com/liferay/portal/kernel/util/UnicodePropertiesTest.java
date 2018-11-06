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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Chow
 * @author Xiangyue Cai
 */
public class UnicodePropertiesTest {

	@Test
	public void testFastLoad() throws Exception {
		_testLoad(
			(props, unicodeProperties) -> unicodeProperties.fastLoad(props),
			false);

		_testLoad(
			(props, unicodeProperties) -> unicodeProperties.fastLoad(props),
			true);
	}

	@Test
	public void testGetProperty() {
		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.put(_TEST_KEY_1, _TEST_VALUE_1);

		Assert.assertEquals(
			_TEST_VALUE_1, unicodeProperties.getProperty(_TEST_KEY_1));
		Assert.assertEquals(
			_TEST_VALUE_1,
			unicodeProperties.getProperty(_TEST_KEY_1, "testDefaultValue"));

		Assert.assertNull(unicodeProperties.getProperty(_TEST_KEY_2));
		Assert.assertEquals(
			"testDefaultValue",
			unicodeProperties.getProperty(_TEST_KEY_2, "testDefaultValue"));
	}

	@Test
	public void testGetToStringLength() {
		UnicodeProperties unicodeProperties = new UnicodeProperties();

		Assert.assertEquals(-1, unicodeProperties.getToStringLength());
	}

	@Test
	public void testIsSafe() {
		UnicodeProperties unicodeProperties1 = new UnicodeProperties();

		Assert.assertFalse(
			"If you create an instance with new UnicodeProperties(), _safe " +
				"will be set to false, then isSafe() should return false",
			unicodeProperties1.isSafe());

		UnicodeProperties unicodeProperties2 = new UnicodeProperties(false);

		Assert.assertFalse(
			"isSafe() should return false if _safe was set false",
			unicodeProperties2.isSafe());

		UnicodeProperties unicodeProperties3 = new UnicodeProperties(true);

		Assert.assertTrue(
			"isSafe() should return false if _safe was set true",
			unicodeProperties3.isSafe());
	}

	@Test
	public void testLoad() throws Exception {
		_testLoad(
			(props, unicodeProperties) -> unicodeProperties.load(props), false);

		_testLoad(
			(props, unicodeProperties) -> unicodeProperties.load(props), true);
	}

	@Test
	public void testPutAll() {
		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.putAll(
			new HashMap<String, String>() {
				{
					put(_TEST_KEY_1, _TEST_VALUE_1);
					put(_TEST_KEY_2, _TEST_VALUE_2);
					put(_TEST_KEY_3, _TEST_VALUE_3);
				}
			});

		_assertUnicodeProperties(
			new String[] {_TEST_VALUE_1, _TEST_VALUE_2, _TEST_VALUE_3},
			new String[] {_TEST_KEY_1, _TEST_KEY_2, _TEST_KEY_3},
			unicodeProperties);
	}

	@Test
	public void testPutLine() {
		_testPutLine(false);
		_testPutLine(true);
	}

	@Test
	public void testRemove() {
		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.put(_TEST_KEY_1, _TEST_VALUE_1);

		Assert.assertNull(unicodeProperties.remove(null));
		_assertUnicodeProperties(
			new String[] {_TEST_VALUE_1}, new String[] {_TEST_KEY_1},
			unicodeProperties);

		Assert.assertEquals(
			_TEST_VALUE_1, unicodeProperties.remove(_TEST_KEY_1));
		Assert.assertTrue(
			"unicodeProperties should be empty after removing testKey1",
			unicodeProperties.isEmpty());
	}

	@Test
	public void testSetProperty() {
		UnicodeProperties unicodeProperties = new UnicodeProperties();

		Assert.assertNull(unicodeProperties.setProperty(null, null));
		Assert.assertTrue(
			"nothing will be put in if key is null",
			unicodeProperties.isEmpty());

		Assert.assertNull(unicodeProperties.setProperty(_TEST_KEY_1, null));
		Assert.assertTrue(
			"nothing will be put in if value is null",
			unicodeProperties.isEmpty());

		Assert.assertNull(
			unicodeProperties.setProperty(_TEST_KEY_1, _TEST_VALUE_1));
		_assertUnicodeProperties(
			new String[] {_TEST_VALUE_1}, new String[] {_TEST_KEY_1},
			unicodeProperties);

		Assert.assertEquals(
			_TEST_VALUE_1, unicodeProperties.setProperty(_TEST_KEY_1, null));
		Assert.assertTrue(
			"setProperty() of null value must remove entry",
			unicodeProperties.isEmpty());
	}

	@Test
	public void testToString() {
		_testToString(false);
		_testToString(true);
	}

	private void _assertUnicodeProperties(
		String[] expectedValues, String[] keys,
		UnicodeProperties unicodeProperties) {

		Assert.assertEquals(
			MapUtil.toString(unicodeProperties), expectedValues.length,
			unicodeProperties.size());

		for (int i = 0; i < keys.length; i++) {
			Assert.assertEquals(
				expectedValues[i], unicodeProperties.get(keys[i]));
		}
	}

	private void _testLoad(
			LoadMethod<String, UnicodeProperties, Exception> loadMethod,
			boolean safe)
		throws Exception {

		UnicodeProperties unicodeProperties = new UnicodeProperties(safe);

		loadMethod.load(null, unicodeProperties);

		Assert.assertTrue(
			"nothing will be put in if props is null",
			unicodeProperties.isEmpty());

		loadMethod.load(_TEST_LINE_1, unicodeProperties);

		_assertUnicodeProperties(
			new String[] {_TEST_VALUE_1}, new String[] {_TEST_KEY_1},
			unicodeProperties);

		loadMethod.load(_TEST_PROPS, unicodeProperties);

		_assertUnicodeProperties(
			new String[] {_TEST_VALUE_1, _TEST_VALUE_2, _TEST_VALUE_3},
			new String[] {_TEST_KEY_1, _TEST_KEY_2, _TEST_KEY_3},
			unicodeProperties);

		loadMethod.load(
			_TEST_LINE_1 + _SAFE_NEWLINE_CHARACTER + StringPool.NEW_LINE +
				_TEST_LINE_2 + _SAFE_NEWLINE_CHARACTER + StringPool.NEW_LINE +
					_TEST_LINE_3 + _SAFE_NEWLINE_CHARACTER +
						StringPool.NEW_LINE,
			unicodeProperties);

		if (safe) {
			_assertUnicodeProperties(
				new String[] {
					_TEST_VALUE_1 + StringPool.NEW_LINE,
					_TEST_VALUE_2 + StringPool.NEW_LINE,
					_TEST_VALUE_3 + StringPool.NEW_LINE
				},
				new String[] {_TEST_KEY_1, _TEST_KEY_2, _TEST_KEY_3},
				unicodeProperties);
		}
		else {
			_assertUnicodeProperties(
				new String[] {
					_TEST_VALUE_1 + _SAFE_NEWLINE_CHARACTER,
					_TEST_VALUE_2 + _SAFE_NEWLINE_CHARACTER,
					_TEST_VALUE_3 + _SAFE_NEWLINE_CHARACTER
				},
				new String[] {_TEST_KEY_1, _TEST_KEY_2, _TEST_KEY_3},
				unicodeProperties);
		}
	}

	private void _testPutLine(boolean safe) {
		UnicodeProperties unicodeProperties = new UnicodeProperties(safe);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					UnicodeProperties.class.getName(), Level.ALL)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 0, logRecords.size());

			// line without EQUAL(=)

			unicodeProperties.put(_TEST_KEY_1);

			Assert.assertTrue(
				"nothing will be put in if line without \"=\"",
				unicodeProperties.isEmpty());

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Invalid property on line " + _TEST_KEY_1,
				logRecord.getMessage());
		}

		// line with empty

		unicodeProperties.put("");

		Assert.assertTrue(
			"nothing will be put in if call put(\"\")",
			unicodeProperties.isEmpty());

		// line with POUND(#)

		unicodeProperties.put("#");

		Assert.assertTrue(
			"nothing will be put in happen if call put(\"#\")",
			unicodeProperties.isEmpty());

		// line with _TEST_LINE_1(testKey1=testValue1)

		unicodeProperties.put(_TEST_LINE_1);

		_assertUnicodeProperties(
			new String[] {_TEST_VALUE_1}, new String[] {_TEST_KEY_1},
			unicodeProperties);

		// safe is true

		unicodeProperties.put(_TEST_LINE_1 + _SAFE_NEWLINE_CHARACTER);

		if (safe) {
			_assertUnicodeProperties(
				new String[] {_TEST_VALUE_1 + StringPool.NEW_LINE},
				new String[] {_TEST_KEY_1}, unicodeProperties);
		}
		else {
			_assertUnicodeProperties(
				new String[] {_TEST_VALUE_1 + _SAFE_NEWLINE_CHARACTER},
				new String[] {_TEST_KEY_1}, unicodeProperties);
		}
	}

	private void _testToString(boolean safe) {
		UnicodeProperties unicodeProperties = new UnicodeProperties(safe);

		Assert.assertEquals(StringPool.BLANK, unicodeProperties.toString());
		Assert.assertEquals(
			StringPool.BLANK, unicodeProperties.toSortedString());

		unicodeProperties.put(_TEST_KEY_1, StringPool.BLANK);

		Assert.assertEquals(StringPool.BLANK, unicodeProperties.toString());
		Assert.assertEquals(
			StringPool.BLANK, unicodeProperties.toSortedString());

		unicodeProperties.put(_TEST_LINE_2);

		Assert.assertEquals(
			_TEST_LINE_2 + StringPool.NEW_LINE, unicodeProperties.toString());
		Assert.assertEquals(
			_TEST_LINE_2 + StringPool.NEW_LINE,
			unicodeProperties.toSortedString());

		unicodeProperties.put(_TEST_LINE_3);

		Assert.assertEquals(
			_TEST_LINE_2 + StringPool.NEW_LINE + _TEST_LINE_3 +
				StringPool.NEW_LINE,
			unicodeProperties.toString());
		Assert.assertEquals(
			_TEST_LINE_2 + StringPool.NEW_LINE + _TEST_LINE_3 +
				StringPool.NEW_LINE,
			unicodeProperties.toSortedString());

		unicodeProperties.put(_TEST_KEY_3, _TEST_VALUE_3 + StringPool.NEW_LINE);

		if (safe) {
			Assert.assertEquals(
				_TEST_LINE_2 + StringPool.NEW_LINE + _TEST_LINE_3 +
					_SAFE_NEWLINE_CHARACTER + StringPool.NEW_LINE,
				unicodeProperties.toString());
			Assert.assertEquals(
				_TEST_LINE_2 + StringPool.NEW_LINE + _TEST_LINE_3 +
					_SAFE_NEWLINE_CHARACTER + StringPool.NEW_LINE,
				unicodeProperties.toSortedString());
		}
		else {
			Assert.assertEquals(
				_TEST_LINE_2 + StringPool.NEW_LINE + _TEST_LINE_3 +
					StringPool.NEW_LINE + StringPool.NEW_LINE,
				unicodeProperties.toString());
			Assert.assertEquals(
				_TEST_LINE_2 + StringPool.NEW_LINE + _TEST_LINE_3 +
					StringPool.NEW_LINE + StringPool.NEW_LINE,
				unicodeProperties.toSortedString());
		}
	}

	private static final String _SAFE_NEWLINE_CHARACTER =
		ReflectionTestUtil.getFieldValue(
			UnicodeProperties.class, "_SAFE_NEWLINE_CHARACTER");

	private static final String _TEST_KEY_1 = "testKey1";

	private static final String _TEST_KEY_2 = "testKey2";

	private static final String _TEST_KEY_3 = "testKey3";

	private static final String _TEST_LINE_1;

	private static final String _TEST_LINE_2;

	private static final String _TEST_LINE_3;

	private static final String _TEST_PROPS;

	private static final String _TEST_VALUE_1 = "testValue1";

	private static final String _TEST_VALUE_2 = "testValue2";

	private static final String _TEST_VALUE_3 = "testValue3";

	static {
		_TEST_LINE_1 = _TEST_KEY_1 + StringPool.EQUAL + _TEST_VALUE_1;

		_TEST_LINE_2 = _TEST_KEY_2 + StringPool.EQUAL + _TEST_VALUE_2;

		_TEST_LINE_3 = _TEST_KEY_3 + StringPool.EQUAL + _TEST_VALUE_3;

		_TEST_PROPS =
			_TEST_LINE_1 + StringPool.NEW_LINE + _TEST_LINE_2 +
				StringPool.NEW_LINE + _TEST_LINE_3;
	}

	private interface LoadMethod<E, U, T extends Throwable> {

		public void load(E e, U u) throws T;

	}

}