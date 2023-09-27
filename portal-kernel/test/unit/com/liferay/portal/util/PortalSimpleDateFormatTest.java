/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalSimpleDateFormat;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.text.DateFormat;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Máté Thurzó
 */
public class PortalSimpleDateFormatTest {

	@Test
	public void testFormat_HHmmss() {
		DateFormat dateFormat = new PortalSimpleDateFormat(
			"HHmmss", LocaleUtil.getDefault());

		dateFormat.setTimeZone(TimeZoneUtil.getDefault());

		Assert.assertEquals("224000", dateFormat.format(getTime()));
	}

	@Test
	public void testFormat_ISO8601() {
		DateFormat dateFormat = new PortalSimpleDateFormat(
			DateUtil.ISO_8601_PATTERN, LocaleUtil.getDefault());

		dateFormat.setTimeZone(TimeZoneUtil.getDefault());

		Assert.assertEquals(
			"1984-03-09T22:40:00+00:00", dateFormat.format(getTime()));
	}

	@Test
	public void testFormat_yyyyMMdd() {
		DateFormat dateFormat = new PortalSimpleDateFormat(
			"yyyyMMdd", LocaleUtil.getDefault());

		dateFormat.setTimeZone(TimeZoneUtil.getDefault());

		Assert.assertEquals("19840309", dateFormat.format(getTime()));
	}

	protected long getTime() {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 1984);
		calendar.set(Calendar.MONTH, Calendar.MARCH);
		calendar.set(Calendar.DAY_OF_MONTH, 9);
		calendar.set(Calendar.HOUR_OF_DAY, 22);
		calendar.set(Calendar.MINUTE, 40);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		calendar.setTimeZone(TimeZoneUtil.getDefault());

		return calendar.getTimeInMillis();
	}

}