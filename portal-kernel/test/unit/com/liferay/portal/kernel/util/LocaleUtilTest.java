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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;

import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Wesley Gong
 */
@PrepareForTest(LanguageUtil.class)
@RunWith(PowerMockRunner.class)
public class LocaleUtilTest extends PowerMockito {

	@After
	public void tearDown() {
		verifyStatic();
	}

	@Test
	public void testFromLanguageId() {
		mockStatic(LanguageUtil.class);

		when(
			LanguageUtil.isAvailableLocale(Locale.US)
		).thenReturn(
			true
		);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LocaleUtil.class.getName(), Level.WARNING)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(Locale.US, LocaleUtil.fromLanguageId("en_US"));
			Assert.assertEquals(logRecords.toString(), 0, logRecords.size());

			logRecords.clear();

			LocaleUtil.fromLanguageId("en");

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"en is not a valid language id", logRecord.getMessage());
		}
	}

}