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

package com.liferay.portal.kernel.map;

import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sampsa Sohlman
 * @author Manuel de la Peña
 * @author Péter Borkuti
 */
public class MapUtilWhenCreatingALinkedHashMapFromArrayTest {

	@Test
	public void testShouldReturnEmptyMapWithParamsInvalid() {
		Map<String, Object> map = MapUtil.toLinkedHashMap(new String[] {"one"});

		Assert.assertTrue(map.toString(), map.isEmpty());

		map = MapUtil.toLinkedHashMap(new String[] {"one:two:three:four"});

		Assert.assertTrue(map.toString(), map.isEmpty());
	}

	@Test
	public void testShouldReturnEmptyMapWithParamsNull() {
		Map<String, Object> map = MapUtil.toLinkedHashMap(null);

		Assert.assertTrue(map.toString(), map.isEmpty());
	}

	@Test
	public void testShouldReturnEmptyMapWithParamsTypeObject() {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					MapUtil.class.getName(), Level.SEVERE)) {

			Map<String, Object> map = MapUtil.toLinkedHashMap(
				new String[] {"one:1:" + Object.class.getName()});

			Assert.assertTrue(map.toString(), map.isEmpty());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"java.lang.Object.<init>(java.lang.String)",
				logRecord.getMessage());

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(
				NoSuchMethodException.class, throwable.getClass());
		}
	}

	@Test
	public void testShouldReturnEmptyMapWithParamsZeroLength() {
		Map<String, String> map = MapUtil.toLinkedHashMap(new String[0]);

		Assert.assertTrue(map.toString(), map.isEmpty());
	}

	@Test
	public void testShouldReturnMapWithDelimiterCustom() {
		Map<String, String> map = MapUtil.toLinkedHashMap(new String[0], ",");

		Assert.assertTrue(map.toString(), map.isEmpty());

		map = MapUtil.toLinkedHashMap(new String[] {"one,1"}, ",");

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue("1"));

		map = MapUtil.toLinkedHashMap(new String[] {"one,1", "two,2"}, ",");

		Assert.assertEquals(map.toString(), 2, map.size());
	}

	@Test
	public void testShouldReturnMapWithDelimiterDefault() {
		Map<String, String> map = MapUtil.toLinkedHashMap(new String[0]);

		Assert.assertTrue(map.toString(), map.isEmpty());

		map = MapUtil.toLinkedHashMap(new String[] {"one:1"});

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertTrue(map.containsKey("one"));
		Assert.assertTrue(map.containsValue("1"));

		map = MapUtil.toLinkedHashMap(new String[] {"one:1", "two:2"});

		Assert.assertEquals(map.toString(), 2, map.size());
	}

}