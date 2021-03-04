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

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ClassLoaderResourceParserTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testGetURL() throws MalformedURLException {
		ClassLoaderResourceParser classLoaderResourceParser =
			new ClassLoaderResourceParser();

		Assert.assertNull(
			classLoaderResourceParser.getURL(
				TemplateConstants.SERVLET_SEPARATOR));
		Assert.assertNull(
			classLoaderResourceParser.getURL(
				TemplateConstants.TEMPLATE_SEPARATOR));
		Assert.assertNull(
			classLoaderResourceParser.getURL(
				TemplateConstants.THEME_LOADER_SEPARATOR));

		String templateId = "DummyFile";

		Assert.assertNull(classLoaderResourceParser.getURL(templateId));

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				ClassLoaderResourceParser.class.getName(), Level.FINEST)) {

			Assert.assertNull(classLoaderResourceParser.getURL(templateId));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals("Loading " + templateId, logEntry.getMessage());
		}

		String contextName = "test-context";

		URL dummyURL = new URL("file://");

		ClassLoaderPool.register(
			contextName,
			new ClassLoader() {

				@Override
				public URL getResource(String name) {
					if (name.equals(templateId)) {
						return dummyURL;
					}

					return null;
				}

			});

		Assert.assertSame(
			dummyURL,
			classLoaderResourceParser.getURL(
				StringBundler.concat(
					contextName, TemplateConstants.CLASS_LOADER_SEPARATOR,
					templateId)));
	}

}