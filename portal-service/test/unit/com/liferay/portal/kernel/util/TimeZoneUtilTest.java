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

import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import java.util.Map;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Will Newbury
 */
public class TimeZoneUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testGetDefaultTimeZone() {
		Assert.assertEquals(
			TimeZone.getTimeZone("UTC"), TimeZoneUtil.getDefault());

		TimeZone timeZone = TimeZone.getTimeZone("PST");

		TimeZoneThreadLocal.setDefaultTimeZone(timeZone);

		Assert.assertEquals(timeZone, TimeZoneUtil.getDefault());
	}

	@Test
	public void testGetInstance() throws NoSuchMethodException {
		TimeZoneUtil timeZoneUtilInstance1 = TimeZoneUtil.getInstance();

		TimeZoneUtil timeZoneUtilInstance2 = TimeZoneUtil.getInstance();

		Assert.assertNotEquals(timeZoneUtilInstance1, timeZoneUtilInstance2);

		Constructor constructor = TimeZoneUtil.class.getDeclaredConstructor();

		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	}

	@Test
	public void testGetTimeZone() {
		Assert.assertNotSame(
			TimeZone.getTimeZone("PST"), TimeZoneUtil.getTimeZone("PST"));

		Assert.assertEquals(
			TimeZone.getTimeZone("PST"), TimeZoneUtil.getTimeZone("PST"));
	}

	@Test
	public void testNewDefaultTimeZone() {
		TimeZoneUtil.setDefault("PST");

		Assert.assertEquals(
			TimeZone.getTimeZone("PST"), TimeZoneUtil.getDefault());

		TimeZoneUtil.setDefault(null);

		Assert.assertEquals(
			TimeZone.getTimeZone("PST"), TimeZoneUtil.getDefault());
	}

	@Test
	public void testTimeZoneStorage() {
		Map<String, TimeZone> timeZones = ReflectionTestUtil.getFieldValue(
			TimeZoneUtil.class, "_timeZones");

		Assert.assertEquals(0, timeZones.size());

		TimeZone timeZone1 = TimeZoneUtil.getTimeZone("PST");

		timeZones = ReflectionTestUtil.getFieldValue(
			TimeZoneUtil.class, "_timeZones");

		Assert.assertEquals(1, timeZones.size());

		TimeZone timeZone2 = TimeZoneUtil.getTimeZone("PST");

		timeZones = ReflectionTestUtil.getFieldValue(
			TimeZoneUtil.class, "_timeZones");

		Assert.assertEquals(1, timeZones.size());

		Assert.assertSame(timeZone1, timeZone2);

		Assert.assertEquals(timeZone1, timeZones.get("PST"));
	}

}