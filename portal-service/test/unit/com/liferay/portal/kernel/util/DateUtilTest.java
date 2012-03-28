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

package com.liferay.portal.kernel.util;

import java.util.TimeZone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Alexander Chow
 * @author Manuel de la Peña
 * @author Ray Augé
 */
@PrepareForTest(DateFormatFactoryUtil.class)
@RunWith(PowerMockRunner.class)
public class DateUtilTest extends PowerMockito {

	@Test
	public void testGetUTCFormat() {
		_testGetUTCFormat("19721223", "yyyyMMdd");
	}

	public void _testGetUTCFormat(String date, String pattern) {
		mockStatic(DateFormatFactoryUtil.class);

		when(
			DateFormatFactoryUtil.getSimpleDateFormat(
				Mockito.anyString(), Mockito.any(TimeZone.class))
		).thenAnswer(
			new Answer<SimpleDateFormat>() {
				public SimpleDateFormat answer(InvocationOnMock invocation)
					throws Throwable {

					return new TestSimpleDateFormat(
						(String)invocation.getArguments()[0]);
				}
			}
		);

		DateFormat utcFormat = DateUtil.getUTCFormat(date);

		Assert.assertNotNull(utcFormat);

		Assert.assertTrue(utcFormat instanceof SimpleDateFormat);

		TestSimpleDateFormat sdf = (TestSimpleDateFormat)utcFormat;

		Assert.assertEquals(sdf.getPattern(), pattern);
	}

	class TestSimpleDateFormat extends SimpleDateFormat {

		public TestSimpleDateFormat(String pattern) {
			super(pattern);

			_pattern = pattern;
		}

		public String getPattern() {
			return _pattern;
		}

		private String _pattern;
	}

}