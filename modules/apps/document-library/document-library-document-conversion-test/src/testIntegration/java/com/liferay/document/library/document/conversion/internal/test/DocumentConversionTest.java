/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.document.conversion.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.document.conversion.DocumentConversion;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.InputStream;

import java.util.Dictionary;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class DocumentConversionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(Propagation.REQUIRED));

	@Test
	public void testCovertThrowsTimeoutExceptionWhenTimeOut() throws Exception {
		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"cacheEnabled", false
			).put(
				"serverEnabled", true
			).build();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.document.conversion." +
						"internal.configuration.OpenOfficeConfiguration",
					dictionary);
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.document.library.document.conversion.internal." +
					"DocumentConversionImpl",
				LoggerTestUtil.ERROR);
			SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DL_FILE_ENTRY_PREVIEW_GENERATION_TIMEOUT_GHOSTSCRIPT", 0L);
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DL_FILE_ENTRY_PREVIEW_GENERATION_TIMEOUT_PDFBOX", 0L)) {

			String id = RandomTestUtil.randomString();

			Class<?> clazz = getClass();

			InputStream inputStream = clazz.getResourceAsStream("/test.xls");

			String sourceExtension = "xls";
			String targetExtension = "pdf";

			try {
				_documentConversion.convert(
					id, inputStream, sourceExtension, targetExtension);
			}
			catch (Exception exception) {
				Throwable throwable = exception.getCause();

				Assert.assertSame(TimeoutException.class, throwable.getClass());
			}
		}
	}

	@Inject
	private DocumentConversion _documentConversion;

}