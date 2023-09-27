/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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