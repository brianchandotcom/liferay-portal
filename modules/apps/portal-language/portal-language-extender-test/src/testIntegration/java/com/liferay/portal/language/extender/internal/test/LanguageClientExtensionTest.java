/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.extender.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Thiago Buarque
 */
@RunWith(Arquillian.class)
public class LanguageClientExtensionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_bundle = FrameworkUtil.getBundle(LanguageClientExtensionTest.class);

		_bundleContext = _bundle.getBundleContext();
	}

	@Test
	public void testAddingBundle() throws Exception {
		Bundle bundle = _bundleContext.installBundle(
			RandomTestUtil.randomString(),
			_getBatchBundleInputStream("batch"));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.language.extender.internal." +
					"LanguageClientExtension",
				LoggerTestUtil.ERROR)) {

			bundle.start();

			Thread.sleep(2000);

			PLOEntry ploEntry = _ploEntryLocalService.fetchPLOEntry(
				TestPropsValues.getCompanyId(), "my-english-key", "en_US");

			Assert.assertEquals("my-english-key", ploEntry.getKey());
			Assert.assertEquals("en_US", ploEntry.getLanguageId());
			Assert.assertEquals("My English value", ploEntry.getValue());

			Assert.assertNull(
				_ploEntryLocalService.fetchPLOEntry(
					TestPropsValues.getCompanyId(), "my-key-with-empty-value",
					"pt_BR"));

			Assert.assertNull(
				_ploEntryLocalService.fetchPLOEntry(
					TestPropsValues.getCompanyId(),
					"file-with-invalid-language-id", "yy_ZZ"));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 3, logEntries.size());

			for (LogEntry logEntry : logEntries) {
				String message = logEntry.getMessage();

				boolean matches = false;

				for (String expectedMessage : _expectedLogMessages) {
					if (message.matches(expectedMessage)) {
						matches = true;

						break;
					}
				}

				if (!matches) {
					Assert.fail(
						StringBundler.concat(
							"Log message \"", message,
							"\" must match one of the following: ",
							_expectedLogMessages));
				}
			}
		}
		finally {
			bundle.uninstall();
		}
	}

	private InputStream _getBatchBundleInputStream(String batchName)
		throws Exception {

		String basePath = StringBundler.concat(
			"com/liferay/portal/language/extender/internal/test/dependencies/",
			batchName, StringPool.SLASH);

		Enumeration<URL> enumeration = _bundle.findEntries(basePath, "*", true);

		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				String urlPath = url.getPath();

				if (urlPath.endsWith(StringPool.SLASH)) {
					continue;
				}

				String zipPath = urlPath.substring(basePath.length());

				if (zipPath.startsWith(StringPool.SLASH)) {
					zipPath = zipPath.substring(1);
				}

				try (InputStream inputStream = url.openStream()) {
					zipWriter.addEntry(zipPath, inputStream);
				}
			}
		}

		return new FileInputStream(zipWriter.getFile());
	}

	private Bundle _bundle;
	private BundleContext _bundleContext;
	private final List<String> _expectedLogMessages = Arrays.asList(
		"Unable to process language file \"Language_pt_BR.properties\". " +
			"Value must not be null.",
		"Unable to process language file \"Language_pt_BR.properties\". Key " +
			"must not be null.",
		"Unable to process language file \"Language_yy_ZZ.properties\". " +
			"Language ID \"yy_ZZ\" is not one of the available language IDs: " +
				"\\[(.*)\\].");

	@Inject
	private PLOEntryLocalService _ploEntryLocalService;

	@Inject
	private ZipWriterFactory _zipWriterFactory;

}