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

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ClassLoaderResourceParserTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@SuppressWarnings("deprecation")
	@Test
	public void testGetURL() {
		ClassLoaderResourceParser classLoaderResourceParser =
			new ClassLoaderResourceParser();

		Assert.assertNull(
			classLoaderResourceParser.getURL(
				TemplateConstants.JOURNAL_SEPARATOR));
		Assert.assertNull(
			classLoaderResourceParser.getURL(
				TemplateConstants.SERVLET_SEPARATOR));
		Assert.assertNull(
			classLoaderResourceParser.getURL(
				TemplateConstants.TEMPLATE_SEPARATOR));
		Assert.assertNull(
			classLoaderResourceParser.getURL(
				TemplateConstants.THEME_LOADER_SEPARATOR));

		Class<?> clazz = getClass();

		classLoaderResourceParser = new ClassLoaderResourceParser(
			clazz.getClassLoader());

		String templateId = "DummyFile";

		Assert.assertNull(classLoaderResourceParser.getURL(templateId));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClassLoaderResourceParser.class.getName(), Level.FINEST)) {

			Assert.assertNull(classLoaderResourceParser.getURL(templateId));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Loading " + templateId, logRecord.getMessage());
		}
	}

}