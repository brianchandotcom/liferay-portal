/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.test.TestCase;
import com.liferay.util.PwdGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * @author Brian Wing Shun Chan
 * @author Ganesh Ram
 */
public class BaseTestCase extends TestCase {

	public BaseTestCase() {
		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}

		InitUtil.initWithSpring();
	}

	protected Date newDate() throws Exception {
		return new Date();
	}

	protected Date newDate(int month, int day, int year) throws Exception {
		Calendar calendar = new GregorianCalendar();

		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.YEAR, year);

		return calendar.getTime();
	}

	protected Date nextDate() throws Exception {
		return new Date();
	}

	protected double nextDouble() throws Exception {
		return CounterLocalServiceUtil.increment();
	}

	protected int nextInt() throws Exception {
		return (int)CounterLocalServiceUtil.increment();
	}

	protected long nextLong() throws Exception {
		return CounterLocalServiceUtil.increment();
	}

	protected boolean randomBoolean() throws Exception {
		return _random.nextBoolean();
	}

	protected String randomString() throws Exception {
		return PwdGenerator.getPassword();
	}

	private Random _random = new Random();

}