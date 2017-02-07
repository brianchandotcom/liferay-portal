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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import java.util.Map;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author William Newbury
 */
public class TimeZoneUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testGetDefault() {
		Assert.assertEquals(
			TimeZone.getTimeZone("UTC"), TimeZoneUtil.getDefault());

		TimeZone timeZone = TimeZone.getTimeZone("PST");

		TimeZoneThreadLocal.setDefaultTimeZone(timeZone);

		Assert.assertEquals(timeZone, TimeZoneUtil.getDefault());
	}

	@Test
	public void testGetInstance() throws NoSuchMethodException {
		TimeZoneUtil timeZoneUtil1 = TimeZoneUtil.getInstance();
		TimeZoneUtil timeZoneUtil2 = TimeZoneUtil.getInstance();

		Assert.assertNotSame(timeZoneUtil1, timeZoneUtil2);

		Constructor<TimeZoneUtil> constructor =
			TimeZoneUtil.class.getDeclaredConstructor();

		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	}

	@Test
	public void testGetTimeZone() {
		Assert.assertNotSame(
			TimeZone.getTimeZone("PST"), TimeZone.getTimeZone("PST"));
		Assert.assertSame(
			TimeZoneUtil.getTimeZone("PST"), TimeZoneUtil.getTimeZone("PST"));
		Assert.assertEquals(
			TimeZone.getTimeZone("PST"), TimeZoneUtil.getTimeZone("PST"));
	}

	@Test
	public void testSetDefault() {
		TimeZone timeZone = TimeZoneUtil.getDefault();

		try {
			TimeZoneUtil.setDefault("PST");

			Assert.assertEquals(
				TimeZone.getTimeZone("PST"), TimeZoneUtil.getDefault());

			TimeZoneUtil.setDefault(null);

			Assert.assertEquals(
				TimeZone.getTimeZone("PST"), TimeZoneUtil.getDefault());
		}
		finally {
			TimeZoneUtil.setDefault(timeZone.getID());
		}
	}

	@Test
	public void testTimeZonesCache() {
		Map<String, TimeZone> timeZones = ReflectionTestUtil.getFieldValue(
			TimeZoneUtil.class, "_timeZones");

		timeZones.clear();

		TimeZone timeZone = TimeZoneUtil.getTimeZone("PST");

		Assert.assertSame(timeZone, TimeZoneUtil.getTimeZone("PST"));

		Assert.assertEquals(timeZones.toString(), 1, timeZones.size());
		Assert.assertSame(timeZone, timeZones.get("PST"));
	}

}