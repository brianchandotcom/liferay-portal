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

package com.liferay.portal.service;

import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.service.impl.PortletLocalServiceImpl;

import java.lang.reflect.Method;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class ValidPortletIdTest {

	@Test
	public void testIsValidPortletId() throws Exception {
		Method method = ReflectionTestUtil.getMethod(
			PortletLocalServiceImpl.class, "_checkValidPortletId",
			String.class);

		PortletLocalServiceImpl portletLocalServiceImpl =
			new PortletLocalServiceImpl();

		method.invoke(portletLocalServiceImpl, "aaa");
		method.invoke(portletLocalServiceImpl, "AAA");
		method.invoke(portletLocalServiceImpl, "123");
		method.invoke(portletLocalServiceImpl, "aA1");
		method.invoke(portletLocalServiceImpl, "aaa_bbb");
		method.invoke(portletLocalServiceImpl, "aaa#bbb");

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					PortletLocalServiceImpl.class.getName(), Level.WARNING)) {

			try {
				method.invoke(
					portletLocalServiceImpl,
					"2_INSTANCE_'\"><script>alert(1)</script>");

				Assert.fail();
			}
			catch (Exception e) {
			}

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				logRecord.toString(),
				"Invalid portlet ID 2_INSTANCE_'\"><script>alert(1)</script>",
				logRecord.getMessage());
		}
	}

}